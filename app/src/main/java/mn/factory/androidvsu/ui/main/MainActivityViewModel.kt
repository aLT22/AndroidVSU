package mn.factory.androidvsu.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.model.adzuna.mapper.JobSearchResultToJobPresentationMapper
import mn.factory.domain.adzuna.interactor.GetJobsInteractor
import mn.factory.domain.adzuna.interactor.request.GetJobsRequest

/**
 * Created by Turkin A. on 06/10/2018.
 */
class MainActivityViewModel(
        private val getJobsInteractor: GetJobsInteractor,
        private val mapperJobs: JobSearchResultToJobPresentationMapper
) : ViewModel() {

    val contentVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val jobsLiveData: MutableLiveData<ArrayList<JobPresentation>> = MutableLiveData()
    val jobsList: ArrayList<JobPresentation> = ArrayList()

    val jobsInteractorRequest = GetJobsRequest()

    init {
        contentVisibility.postValue(false)
        fetchJobs()
    }

    fun fetchJobs() {
        getJobsInteractor
                .execute(jobsInteractorRequest)
                .doOnSubscribe { contentVisibility.postValue(false) }
                .map { mapperJobs.map(it) }
                .retry(5)
                .subscribe(
                        { entity ->
                            entity.results?.let { jobsList.addAll(it) }
                            jobsLiveData.postValue(jobsList)
                        },
                        {
                            Log.e(TAG, it.message)
                        },
                        {
                            contentVisibility.postValue(true)
                        }
                )

    }

    fun resetRequest() {
        jobsInteractorRequest.apply {
            resultsPerPage = 10
            page = 1
        }
    }

    companion object {
        const val TAG = "MainActivityViewModel"
    }

}