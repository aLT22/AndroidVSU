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
    val jobsLiveData: MutableLiveData<List<JobPresentation>> = MutableLiveData()

    init {
        contentVisibility.postValue(false)
        fetchJobs()
    }

    private fun fetchJobs() {
        val jobsInteractorRequest = GetJobsRequest()
        jobsInteractorRequest.resultsPerPage = 20

        getJobsInteractor
                .execute(jobsInteractorRequest)
                .doOnSubscribe { contentVisibility.postValue(false) }
                .doOnComplete { contentVisibility.postValue(true) }
                .doOnError { Log.e(TAG, it.message) }
                .map { mapperJobs.map(it) }
                .doOnNext { entity ->
                    jobsLiveData.postValue(entity.results)
                }
                .subscribe()

    }

    companion object {
        const val TAG = "MainActivityViewModel"
    }

}