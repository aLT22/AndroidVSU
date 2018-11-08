package mn.factory.data.api.adzuna.model.car

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 08/11/2018.
 */

data class CarSearchResultsNetwork(
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