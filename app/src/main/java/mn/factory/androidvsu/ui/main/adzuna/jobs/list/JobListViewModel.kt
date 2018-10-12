package mn.factory.androidvsu.ui.main.adzuna.jobs.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.model.adzuna.mapper.JobSearchResultToJobPresentationMapper
import mn.factory.domain.adzuna.interactor.GetJobsInteractor
import mn.factory.domain.adzuna.interactor.request.GetJobsRequest

/**
 * Created by Turkin A. on 12/10/2018.
 */
class JobListViewModel(
        private val getJobsInteractor: GetJobsInteractor,
        private val mapperJobs: JobSearchResultToJobPresentationMapper
) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val jobsLiveData: MutableLiveData<ArrayList<JobPresentation>> = MutableLiveData()
    var jobsList: ArrayList<JobPresentation> = ArrayList()

    val jobsInteractorRequest = GetJobsRequest()

    init {
        loading.postValue(true)
        fetchJobs(true)
    }

    fun fetchJobs(isRefresh: Boolean) {
        getJobsInteractor
                .execute(jobsInteractorRequest)
                .doOnSubscribe { loading.postValue(true) }
                .map { mapperJobs.map(it) }
                .retry(5)
                .subscribe(
                        { entity ->
                            if (isRefresh) {
                                entity.results?.let { jobsList = it as ArrayList<JobPresentation> }
                            } else {
                                entity.results?.let { jobsList.addAll(it) }
                            }
                            jobsLiveData.postValue(jobsList)
                        },
                        {
                            Log.e(TAG, it.message)
                        },
                        {
                            loading.postValue(false)
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
        const val TAG = "JobListVM"
    }
}