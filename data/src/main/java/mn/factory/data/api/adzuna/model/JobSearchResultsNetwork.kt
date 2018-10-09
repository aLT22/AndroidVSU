package mn.factory.data.api.adzuna.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class JobSearchResultsNetwork(
        @SerializedName("count")
        @Expose
        val count: Int?,
        @SerializedName("mean")
        @Expose
        val mean: Double?,
        @SerializedName("results")
        @Expose
        val results: List<ResultNetwork>?,
        @SerializedName("__CLASS__")
        @Expose
        val classType: String?
)