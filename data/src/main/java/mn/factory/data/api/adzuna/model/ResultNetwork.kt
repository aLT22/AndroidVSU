package mn.factory.data.api.adzuna.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class ResultNetwork(
        @SerializedName("__CLASS__")
        @Expose
        val classType: String?,
        @SerializedName("location")
        @Expose
        val location: LocationNetwork?,
        @SerializedName("salary_min")
        @Expose
        val salaryMin: Double?,
        @SerializedName("salary_is_predicted")
        @Expose
        val salaryIsPredicted: String?,
        @SerializedName("redirect_url")
        @Expose
        val redirectUrl: String?,
        @SerializedName("latitude")
        @Expose
        val latitude: Double?,
        @SerializedName("company")
        @Expose
        val company: CompanyNetwork?,
        @SerializedName("category")
        @Expose
        val category: CategoryNetwork?,
        @SerializedName("contract_type")
        @Expose
        val contractType: String?,
        @SerializedName("salary_max")
        @Expose
        val salaryMax: Double?,
        @SerializedName("created")
        @Expose
        val created: String?,
        @SerializedName("title")
        @Expose
        val title: String?,
        @SerializedName("id")
        @Expose
        val id: Int?,
        @SerializedName("description")
        @Expose
        val description: String?,
        @SerializedName("contract_time")
        @Expose
        val contractTime: String?,
        @SerializedName("adref")
        @Expose
        val adref: String?,
        @SerializedName("longitude")
        @Expose
        val longitude: Double?
)