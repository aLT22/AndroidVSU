package mn.factory.androidvsu.ui.main.adzuna.jobs.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mn.factory.androidvsu.model.adzuna.job.JobPresentation

/**
 * Created by Turkin A. on 25/10/2018.
 */
class JobDetailsVM : ViewModel() {

    var mJobPresentation: JobPresentation? = null

    val mTitle = MutableLiveData<String>()
    val mTitleVisibility = MutableLiveData<Boolean>()

    val mDescription = MutableLiveData<String>()
    val mDescriptionVisibility = MutableLiveData<Boolean>()

    val mCompany = MutableLiveData<String>()
    val mCompanyVisibility = MutableLiveData<Boolean>()

    val mLocation = MutableLiveData<String>()
    val mLocationVisibility = MutableLiveData<Boolean>()

    val mSalary = MutableLiveData<String>()
    val mSalaryVisibility = MutableLiveData<Boolean>()
    val mSalaryOption = MutableLiveData<Int>()

    val mContract = MutableLiveData<String>()
    val mContractVisibility = MutableLiveData<Boolean>()

    val mInfo = MutableLiveData<String>()
    val mInfoVisibility = MutableLiveData<Boolean>()

    init {
        mSalaryOption.postValue(PER_YEAR)
    }

    companion object {
        const val TAG = "JobDetailsVM"

        const val PER_YEAR = 0
        const val PER_MONTH = 1
    }

}