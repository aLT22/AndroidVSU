package mn.factory.androidvsu.ui.main.adzuna.jobs.details

import android.text.Spanned
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mn.factory.androidvsu.model.adzuna.job.JobPresentation

/**
 * Created by Turkin A. on 25/10/2018.
 */
class JobDetailsVM : ViewModel() {

    var mJobPresentation: JobPresentation? = null

    val title = MutableLiveData<String?>()
    val mDescription = MutableLiveData<Spanned>()
    val mDescriptionString = MutableLiveData<String>()

}