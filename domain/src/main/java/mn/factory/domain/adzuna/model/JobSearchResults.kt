package mn.factory.domain.adzuna.model

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class JobSearchResults(
        val count: Int?,
        val mean: Double?,
        val results: List<Result>?,
        val classType: String?
)