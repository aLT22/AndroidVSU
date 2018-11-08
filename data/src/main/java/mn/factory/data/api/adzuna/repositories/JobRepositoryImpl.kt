package mn.factory.data.api.adzuna.repositories

import io.reactivex.Single
import mn.factory.data.api.adzuna.AdzunaService
import mn.factory.data.api.adzuna.mapper.JobSearchResultNetworkToJobSearchResultMapper
import mn.factory.domain.adzuna.repositories.JobRepository
import mn.factory.domain.adzuna.model.JobSearchResults

/**
 * Created by Turkin A. on 08/11/2018.
 */
class JobRepositoryImpl(
        private val adzunaService: AdzunaService,
        private val mapperJobResults: JobSearchResultNetworkToJobSearchResultMapper
) : JobRepository {

    override fun getJobs(country: String, page: Int, resultsPerPage: Int): Single<JobSearchResults> =
            adzunaService
                    .getJobs(country, page, resultsPerPage)
                    .map { mapperJobResults.map(it) }

}