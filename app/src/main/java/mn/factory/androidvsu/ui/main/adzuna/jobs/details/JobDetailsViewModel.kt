package mn.factory.androidvsu.ui.main.adzuna.jobs.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import mn.factory.androidvsu.model.adzuna.job.JobPresentation

/**
 * Created by Turkin A. on 25/10/2018.
 */
class JobDetailsViewModel : ViewModel() {

    var mJobPresentation: JobPresentation? = null

    val title = MutableLiveData<String?>()

}