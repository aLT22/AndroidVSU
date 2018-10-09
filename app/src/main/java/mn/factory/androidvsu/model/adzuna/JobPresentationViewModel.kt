package mn.factory.androidvsu.model.adzuna

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
    val salary: ObservableField<String?> =
            ObservableField(
                    "${jobPresentation.salaryMin}£ - ${jobPresentation.salaryMax}£"
            )

}