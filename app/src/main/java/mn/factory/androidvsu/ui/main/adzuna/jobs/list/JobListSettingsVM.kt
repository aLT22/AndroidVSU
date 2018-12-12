package mn.factory.androidvsu.ui.main.adzuna.jobs.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import mn.factory.androidvsu.R
import java.util.*

/**
 * Created by Turkin A. on 11/12/2018.
 */
class JobListSettingsVM(
        private val context: Application
) : AndroidViewModel(context) {

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

    init {
        mSalaryOption.value = SALARY_PER_YEAR
        mSalaryMin.value = "0"
        mSalaryMax.value = "5000000"

        mSalaryTitle.addSource(mSalaryMin) {
            postSalaryTitle(mSalaryOption.value!!)
        }
        mSalaryTitle.addSource(mSalaryMax) {
            postSalaryTitle(mSalaryOption.value!!)
        }
        mSalaryTitle.addSource(mSalaryOption) {
            postSalaryTitle(it)
        }
    }

    private fun postSalaryTitle(salaryOption: Int) {
        when (salaryOption) {
            SALARY_PER_YEAR -> {
                mSalaryTitle
                        .postValue(
                                String.format(
                                        Locale.getDefault(),
                                        context.resources.getString(R.string.salary_range),
                                        mSalaryMin.value,
                                        mSalaryMax.value
                                )
                        )
            }
            SALARY_PER_MONTH -> {
                mSalaryTitle
                        .postValue(
                                String.format(
                                        Locale.getDefault(),
                                        context.resources.getString(R.string.salary_range),
                                        (mSalaryMin.value?.toInt()?.div(12)).toString(),
                                        (mSalaryMax.value?.toInt()?.div(12)).toString()
                                )
                        )
            }
        }
    }

    companion object {
        const val TAG = "JobsSettingVM"

        const val SALARY_PER_YEAR = 0
        const val SALARY_PER_MONTH = 1
    }

}