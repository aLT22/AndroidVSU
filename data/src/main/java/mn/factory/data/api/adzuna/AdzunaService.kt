package mn.factory.data.api.adzuna

import android.support.annotation.StringDef
import io.reactivex.Single
import mn.factory.data.api.adzuna.model.car.CarSearchResultsNetwork
import mn.factory.data.api.adzuna.model.job.JobSearchResultsNetwork
import mn.factory.data.api.adzuna.model.version.VersionNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Turkin A. on 05/10/2018.
 */
interface AdzunaService {

    @StringDef(SORT_DIRECTION_DOWN, SORT_DIRECTION_UP)
    annotation class SortDirection

    @StringDef(SORT_BY_DATE, SORT_BY_SALARY, SORT_BY_RELEVANCE)
    annotation class SortBy

    @StringDef(JOB_TYPE_NONE, JOB_TYPE_VALUE)
    annotation class JobType

    @GET("version")
    fun getVersion(): Single<VersionNetwork>

    /*
    * Jobs block starts
    * */
    @GET("jobs/{country}/search/{page}")
    fun getJobs(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int): Single<JobSearchResultsNetwork>

    @GET("jobs/{country}/search/{page}")
    fun getJobs(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int,
                @Query("max_days_old") maxDaysOld: Int): Single<JobSearchResultsNetwork>

    @GET("jobs/{country}/search/{page}")
    fun getJobs(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int,
                @Query("max_days_old") maxDaysOld: Int,
                @Query("sort_direction") @SortDirection sortDirection: String): Single<JobSearchResultsNetwork>

    @GET("jobs/{country}/search/{page}")
    fun getJobs(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int,
                @Query("max_days_old") maxDaysOld: Int,
                @Query("sort_direction") @SortDirection sortDirection: String,
                @Query("sort_by") @SortBy sortBy: String): Single<JobSearchResultsNetwork>

    @GET("jobs/{country}/search/{page}")
    fun getJobs(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int,
                @Query("max_days_old") maxDaysOld: Int,
                @Query("sort_direction") @SortDirection sortDirection: String,
                @Query("sort_by") @SortBy sortBy: String,
                @Query("full_time") @JobType fullTime: String,
                @Query("part_time") @JobType partTime: String,
                @Query("contract") @JobType contract: String,
                @Query("permanent") @JobType permanent: String): Single<JobSearchResultsNetwork>
    /*
    * Jobs block ends
    * */

    /*
    * Cars block starts
    * */
    @GET("cars/{country}/search/{page}")
    fun getCars(@Path("country") country: String,
                @Path("page") page: Int,
                @Query("results_per_page") resultsPerPage: Int): Single<CarSearchResultsNetwork>
    /*
    * Cars block ends
    * */

    companion object {
        const val SORT_DIRECTION_UP = "up"
        const val SORT_DIRECTION_DOWN = "down"

        const val SORT_BY_DATE = "date"
        const val SORT_BY_SALARY = "salary"
        const val SORT_BY_RELEVANCE = "relevance"

        const val JOB_TYPE_NONE = ""
        const val JOB_TYPE_VALUE = "1"
    }
}