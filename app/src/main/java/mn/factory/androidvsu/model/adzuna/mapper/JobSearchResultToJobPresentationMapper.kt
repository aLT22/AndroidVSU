package mn.factory.androidvsu.model.adzuna.mapper

import mn.factory.androidvsu.model.adzuna.JobPresentation
import mn.factory.androidvsu.model.adzuna.JobResultPresentation
import mn.factory.domain.adzuna.model.JobSearchResults
import mn.factory.domain.adzuna.model.Result
import mn.factory.domain.utils.Mapper

/**
 * Created by Turkin A. on 07/10/2018.
 */
class JobSearchResultToJobPresentationMapper : Mapper<JobSearchResults, JobResultPresentation> {

    override fun map(from: JobSearchResults): JobResultPresentation =
            JobResultPresentation(
                    from.count,
                    from.results?.let { getAdzunaJobResultPresentation(it) }
            )

    private fun getAdzunaJobResultPresentation(results: List<Result>): List<JobPresentation> {
        val adzunaJobResultsPresentation = ArrayList<JobPresentation>()

        for (result in results) {
            adzunaJobResultsPresentation
                    .add(
                            JobPresentation(
                                    result.id,
                                    result.title,
                                    result.description,
                                    result.latitude,
                                    result.longitude,
                                    result.company,
                                    result.location,
                                    result.salaryMin,
                                    result.salaryMax
                            )
                    )
        }

        return adzunaJobResultsPresentation
    }
}