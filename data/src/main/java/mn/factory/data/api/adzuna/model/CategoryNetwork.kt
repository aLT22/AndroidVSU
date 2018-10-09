package mn.factory.data.api.adzuna.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class CategoryNetwork(
        @SerializedName("label")
        @Expose
        val label: String?,
        @SerializedName("__CLASS__")
        @Expose
        val classType: String?,
        @SerializedName("tag")
        @Expose
        val tag: String?
)