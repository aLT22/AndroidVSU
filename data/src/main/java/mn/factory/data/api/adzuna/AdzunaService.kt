package mn.factory.data.api.adzuna

import io.reactivex.Single
import mn.factory.data.api.adzuna.model.JobSearchResultsNetwork
import mn.factory.data.api.adzuna.model.VersionNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Turkin A. on 05/10/2018.
 */
interface AdzunaService {

    @GET("version")
    fun getVersion(): Single<VersionNetwork>

    @GET("jobs/{country}/search/{page}")
    fun getJobs(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int): Single<JobSearchResultsNetwork>

}