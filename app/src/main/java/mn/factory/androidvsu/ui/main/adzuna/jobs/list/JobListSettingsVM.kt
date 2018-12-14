package mn.factory.androidvsu.ui.main.adzuna.jobs.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import mn.factory.androidvsu.R
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Turkin A. on 11/12/2018.
 */
class JobListSettingsVM(
        private val context: Application
) : AndroidViewModel(context), CoroutineScope {

    val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    val mCountry = MutableLiveData<String>()

    val mKeywords = MutableLiveData<String>()

    val mKeywordsTitle = MutableLiveData<String>()

    val mGeographicCenter = MutableLiveData<String>()

    val mSearchOrder = MutableLiveData<String>()

    val mSortOrder = MutableLiveData<String>()

    val mJobTypeByTime = MutableLiveData<String>()

    val mJobTypeByContract = MutableLiveData<String>()

    val mSalaryTitle = MediatorLiveData<String>()
    val mSalaryMin = MutableLiveData<String>()
    val mSalaryMax = MutableLiveData<String>()
    val mSalaryOption = MutableLiveData<Int>()

    val mChangeWatcher = MediatorLiveData<Boolean>()

    init {
        mSalaryOption.value = SALARY_PER_YEAR
        mSalaryMin.value = "0"
        mSalaryMax.value = "5000000"

        mChangeWatcher.value = false

        mSalaryTitle.addSource(mSalaryMin) {
            postSalaryTitle(mSalaryOption.value!!)
        }
        mSalaryTitle.addSource(mSalaryMax) {
            postSalaryTitle(mSalaryOption.value!!)
        }
        mSalaryTitle.addSource(mSalaryOption) {
            postSalaryTitle(it)
        }

        mChangeWatcher.addSource(mCountry) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mKeywords) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mGeographicCenter) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mSearchOrder) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mSortOrder) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mJobTypeByTime) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mJobTypeByContract) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mSalaryMin) {
            postSettingsChanges()
        }
        mChangeWatcher.addSource(mSalaryMax) {
            postSettingsChanges()
        }
    }

    private fun postSalaryTitle(salaryOption: Int) {
        when (salaryOption) {
            SALARY_PER_YEAR -> {
                launch {
                    mSalaryTitle
                            .postValue(
                                    withContext(coroutineContext) {
                                        String.format(
                                                Locale.getDefault(),
                                                context.resources.getString(R.string.salary_range),
                                                mSalaryMin.value,
                                                mSalaryMax.value
                                        )
                                    }
                            )
                }
            }
            SALARY_PER_MONTH -> {
                launch {
                    mSalaryTitle
                            .postValue(
                                    withContext(coroutineContext) {
                                        String.format(
                                                Locale.getDefault(),
                                                context.resources.getString(R.string.salary_range),
                                                (mSalaryMin.value?.toInt()?.div(12)).toString(),
                                                (mSalaryMax.value?.toInt()?.div(12)).toString()
                                        )
                                    }
                            )
                }
            }
        }
    }

    private fun postSettingsChanges() = launch {
        mChangeWatcher.postValue(true)
    }

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    companion object {
        const val TAG = "JobsSettingVM"

        const val SALARY_PER_YEAR = 0
        const val SALARY_PER_MONTH = 1
    }

}