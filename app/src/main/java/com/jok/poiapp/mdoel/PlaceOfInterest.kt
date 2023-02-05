package com.jok.poiapp.mdoel

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pois")

data class PlaceOfInterest (
    val geocoordinates: String = "",
    @PrimaryKey
    val id: String = "",
    val image: String = "",
    val title: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(geocoordinates)
        parcel.writeString(id)
        parcel.writeString(image)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlaceOfInterest> {
        override fun createFromParcel(parcel: Parcel): PlaceOfInterest {
            return PlaceOfInterest(parcel)
        }

        override fun newArray(size: Int): Array<PlaceOfInterest?> {
            return arrayOfNulls(size)
        }
    }
}