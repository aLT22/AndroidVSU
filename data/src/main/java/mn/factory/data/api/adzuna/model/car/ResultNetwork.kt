package mn.factory.data.api.adzuna.model.car

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 08/11/2018.
 */
data class ResultNetwork(
        @SerializedName("title")
        @Expose
        val title: String?,
        @SerializedName("price")
        @Expose
        val price: Int?,
        @SerializedName("description")
        @Expose
        val description: String?,
        @SerializedName("location")
        @Expose
        val location: LocationNetwork?,
        @SerializedName("transmission")
        @Expose
        val transmission: String?,
        @SerializedName("longitude")
        @Expose
        val longitude: Double?,
        @SerializedName("adref")
        @Expose
        val adref: String?,
        @SerializedName("year")
        @Expose
        val year: Int?,
        @SerializedName("id")
        @Expose
        val id: Int?,
        @SerializedName("__CLASS__")
        @Expose
        val classType: String?,
        @SerializedName("redirect_url")
        @Expose
        val redirectUrl: String?,
        @SerializedName("model")
        @Expose
        val model: ModelNetwork?,
        @SerializedName("image_url")
        @Expose
        val imageUrl: String?,
        @SerializedName("latitude")
        @Expose
        val latitude: Double?,
        @SerializedName("mileage")
        @Expose
        val mileage: Int?,
        @SerializedName("category")
        @Expose
        val category: CategoryNetwork?,
        @SerializedName("created")
        @Expose
        val created: String?
)