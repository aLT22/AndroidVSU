package mn.factory.androidvsu.ui.main.adzuna.jobs.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.mapper.JobSearchResultToJobPresentationMapper
import mn.factory.androidvsu.utils.exts.extendedErrorMessage
import mn.factory.domain.adzuna.interactors.GetJobsInteractor
import mn.factory.domain.adzuna.interactors.request.GetJobsRequest

/**
 * Created by Turkin A. on 12/10/2018.
 */
class JobListVM(
        private val getJobsInteractor: GetJobsInteractor,
        private val mapperJobs: JobSearchResultToJobPresentationMapper
) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    private val jobsLiveData: MutableLiveData<ArrayList<JobPresentation>> = MutableLiveData()
    var jobsList: ArrayList<JobPresentation> = ArrayList()

    val jobsInteractorRequest = GetJobsRequest()

    fun getJobs(): LiveData<ArrayList<JobPresentation>> = jobsLiveData

    fun resetRequest() {
        jobsInteractorRequest.apply {
            resultsPerPage = 20
            page = 1
        }
    }

    @SuppressLint("CheckResult")
    fun fetchJobs(isRefresh: Boolean) {
        getJobsInteractor
                .execute(jobsInteractorRequest)
                .doOnSubscribe { loading.postValue(true) }
                .map { mapperJobs.map(it) }
                .retry(1)
                .extendedErrorMessage()
                .subscribe(
                        { entity ->
                            if (isRefresh) {
                                entity.results?.let { jobsList = it as ArrayList<JobPresentation> }
                            } else {
                                entity.results?.let { jobsList.addAll(it) }
                            }
                            jobsLiveData.value = jobsList
                        },
                        {
                            Log.e(TAG, it.message)
                            loading.postValue(false)
                        },
                        {
                            loading.postValue(false)
                        }
                )

    }

    companion object {
        const val TAG = "JobListVM"
    }
}