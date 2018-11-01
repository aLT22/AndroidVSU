package mn.factory.androidvsu.model.adzuna.job

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobPresentationViewModel(
        jobPresentation: JobPresentation
) : ViewModel() {

    val title: ObservableField<String?> = ObservableField(jobPresentation.title)
    val description: ObservableField<String?> = ObservableField(jobPresentation.description)
    val latitude: ObservableField<String?> = ObservableField(jobPresentation.latitude.toString())
    val longitude: ObservableField<String?> = ObservableField(jobPresentation.longitude.toString())
    val company: ObservableField<String?> = ObservableField(jobPresentation.company?.displayName)
    val location: ObservableField<String?> = ObservableField(jobPresentation.location?.displayName)

    companion object {
        const val TITLE = "TITLE"
        const val DESCRIPTION = "DESCRIPTION"
        const val COMPANY_TITLE = "COMPANY_TITLE"
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val LOCATION = "LOCATION"
        const val SALARY_MIN = "SALARY_MIN"
        const val SALARY_MAX = "SALARY_MAX"
    }
}