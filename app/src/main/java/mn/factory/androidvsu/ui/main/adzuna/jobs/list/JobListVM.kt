package mn.factory.androidvsu.ui.main.adzuna.jobs.list

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mn.factory.androidvsu.model.adzuna.job.JobPresentation
import mn.factory.androidvsu.model.adzuna.mapper.JobSearchResultToJobPresentationMapper
import mn.factory.androidvsu.utils.exts.extendedErrorMessage
import mn.factory.domain.adzuna.interactors.GetJobsInteractor
import mn.factory.domain.adzuna.interactors.request.GetJobsRequest
import kotlin.coroutines.CoroutineContext

/**
 * Created by Turkin A. on 12/10/2018.
 */
class JobListVM(
        private val getJobsInteractor: GetJobsInteractor,
        private val mapperJobs: JobSearchResultToJobPresentationMapper
) : ViewModel(), CoroutineScope {

    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

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
                            launch {
                                try {
                                    if (isRefresh) {
                                        entity.results?.let { jobsList = it as ArrayList<JobPresentation> }
                                    } else {
                                        entity.results?.let { jobsList.addAll(it) }
                                    }
                                    jobsLiveData.postValue(jobsList)
                                } catch (exception: Exception) {
                                    Log.d(TAG, exception.message)
                                    exception.printStackTrace()
                                }
                            }
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

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    companion object {
        const val TAG = "JobListVM"
    }
}