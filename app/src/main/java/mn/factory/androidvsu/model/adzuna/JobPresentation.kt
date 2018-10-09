package mn.factory.androidvsu.model.adzuna

import mn.factory.domain.adzuna.model.Company
import mn.factory.domain.adzuna.model.Location

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class JobPresentation(
        val id: Int?,
        val title: String?,
        val description: String?,
        val latitude: Double?,
        val longitude: Double?,
        val company: Company?,
        val location: Location?,
        val salaryMin: Double?,
        val salaryMax: Double?
)