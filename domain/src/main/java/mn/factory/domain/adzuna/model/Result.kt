package mn.factory.domain.adzuna.model

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class Result(
        val classType: String?,
        val location: Location?,
        val salaryMin: Double?,
        val salaryIsPredicted: String?,
        val redirectUrl: String?,
        val latitude: Double?,
        val company: Company?,
        val category: Category?,
        val contractType: String?,
        val salaryMax: Double?,
        val created: String?,
        val title: String?,
        val id: Int?,
        val description: String?,
        val contractTime: String?,
        val adref: String?,
        val longitude: Double?
)