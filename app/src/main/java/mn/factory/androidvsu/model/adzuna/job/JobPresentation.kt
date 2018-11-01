package mn.factory.androidvsu.model.adzuna.job

import android.os.Parcel
import android.os.Parcelable
import mn.factory.androidvsu.model.ItemPresentation
import mn.factory.domain.adzuna.model.Company
import mn.factory.domain.adzuna.model.Location

/**
 * Created by Turkin A. on 07/10/2018.
 */

data class JobPresentation(
        val id: Int?,
        val title: String?,
        val description: String?,
        val latitude: Double?,
        val longitude: Double?,
        val company: Company?,
        val location: Location?,
        val salaryMin: Double?,
        val salaryMax: Double?
) : ItemPresentation, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble(),
            Company(parcel.readString(), parcel.readString()),
            Location(parcel.readString(), parcel.readString(), parcel.createStringArrayList()),
            parcel.readDouble(),
            parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let { parcel.writeInt(it) }
        title?.let { parcel.writeString(it) }
        description?.let { parcel.writeString(it) }
        latitude?.let { parcel.writeDouble(it) }
        longitude?.let { parcel.writeDouble(it) }
        company?.let { parcel.writeString(it.displayName) }
        company?.let { parcel.writeString(it.classType) }
        location?.let { parcel.writeString(it.displayName) }
        location?.let { parcel.writeString(it.classType) }
        location?.let { parcel.writeStringList(it.area) }
        salaryMin?.let { parcel.writeDouble(it) }
        salaryMax?.let { parcel.writeDouble(it) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JobPresentation> {
        override fun createFromParcel(parcel: Parcel): JobPresentation {
            return JobPresentation(parcel)
        }

        override fun newArray(size: Int): Array<JobPresentation?> {
            return arrayOfNulls(size)
        }
    }
}