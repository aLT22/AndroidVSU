package mn.factory.data.api.adzuna.model.car

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 08/11/2018.
 */
data class LocationNetwork(
        @SerializedName("display_name")
        @Expose
        val displayName: String?,
        @SerializedName("__CLASS__")
        @Expose
        val classType: String?,
        @SerializedName("area")
        @Expose
        val area: List<String>?
)