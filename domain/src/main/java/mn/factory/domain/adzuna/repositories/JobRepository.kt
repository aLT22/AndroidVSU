package mn.factory.domain.adzuna.repositories

import io.reactivex.Single
import mn.factory.domain.adzuna.model.JobSearchResults

/**
 * Created by Turkin A. on 08/11/2018.
 */
interface JobRepository : Repository {

    fun getJobs(country: String, page: Int, resultsPerPage: Int): Single<JobSearchResults>

}