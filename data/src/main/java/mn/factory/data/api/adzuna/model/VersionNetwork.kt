package mn.factory.data.api.adzuna.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 05/10/2018.
 */

data class VersionNetwork(
        @SerializedName("software_version")
        @Expose
        val softwareVersion: String?,
        @SerializedName("__CLASS__")
        @Expose
        val classType: String?,
        @SerializedName("api_version")
        @Expose
        val apiVersion: Int?
)