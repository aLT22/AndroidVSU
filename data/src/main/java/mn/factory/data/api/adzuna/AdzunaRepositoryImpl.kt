package mn.factory.data.api.adzuna

import io.reactivex.Single
import mn.factory.data.api.adzuna.mapper.JobSearchResultNetworkToJobSearchResultMapper
import mn.factory.data.api.adzuna.mapper.VersionNetworkToVersionMapper
import mn.factory.domain.adzuna.AdzunaRepository
import mn.factory.domain.adzuna.model.JobSearchResults
import mn.factory.domain.adzuna.model.Version

/**
 * Created by Turkin A. on 05/10/2018.
 */
class AdzunaRepositoryImpl(
        private val adzunaService: AdzunaService,
        private val mapperVersion: VersionNetworkToVersionMapper,
        private val mapperJobResults: JobSearchResultNetworkToJobSearchResultMapper
) : AdzunaRepository {

    override fun getVersion(): Single<Version> =
            adzunaService
                    .getVersion()
                    .map { mapperVersion.map(it) }

    override fun getJobs(country: String, page: Int, resultsPerPage: Int): Single<JobSearchResults> =
            adzunaService
                    .getJobs(country, page, resultsPerPage)
                    .map { mapperJobResults.map(it) }
}