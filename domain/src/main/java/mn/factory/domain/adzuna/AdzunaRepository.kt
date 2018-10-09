package mn.factory.domain.adzuna

import io.reactivex.Single
import mn.factory.domain.adzuna.model.JobSearchResults
import mn.factory.domain.adzuna.model.Version

/**
 * Created by Turkin A. on 05/10/2018.
 */
interface AdzunaRepository {

    fun getVersion(): Single<Version>

    fun getJobs(country: String, page: Int, resultsPerPage: Int): Single<JobSearchResults>

}