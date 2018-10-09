package mn.factory.data.api.adzuna.mapper

import mn.factory.data.api.adzuna.model.*
import mn.factory.domain.adzuna.model.*
import mn.factory.domain.utils.Mapper

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobSearchResultNetworkToJobSearchResultMapper : Mapper<JobSearchResultsNetwork, JobSearchResults> {

    override fun map(from: JobSearchResultsNetwork): JobSearchResults =
            JobSearchResults(
                    from.count,
                    from.mean,
                    getAdzunaResults(from.results),
                    from.classType
            )

    private fun getAdzunaResults(resultsNetwork: List<ResultNetwork>?): List<Result>? {
        val adzunaResults = ArrayList<Result>()

        if (resultsNetwork != null) {
            for (result in resultsNetwork) {
                adzunaResults
                        .add(
                                Result(
                                        result.classType,
                                        result.location?.let { getAdzunaLocation(it) },
                                        result.salaryMin,
                                        result.salaryIsPredicted,
                                        result.redirectUrl,
                                        result.latitude,
                                        result.company?.let { getAdzunaCompany(it) },
                                        result.category?.let { getAdzunaCategory(it) },
                                        result.contractType,
                                        result.salaryMax,
                                        result.created,
                                        result.title,
                                        result.id,
                                        result.description,
                                        result.contractTime,
                                        result.adref,
                                        result.longitude
                                )
                        )
            }
        }

        return adzunaResults
    }

    private fun getAdzunaLocation(locationNetwork: LocationNetwork): Location {
        return Location(
                locationNetwork.displayName,
                locationNetwork.classType,
                locationNetwork.area
        )
    }

    private fun getAdzunaCompany(companyNetwork: CompanyNetwork): Company {
        return Company(
                companyNetwork.classType,
                companyNetwork.displayName
        )
    }

    private fun getAdzunaCategory(categoryNetwork: CategoryNetwork): Category {
        return Category(
                categoryNetwork.label,
                categoryNetwork.classType,
                categoryNetwork.tag
        )
    }
}