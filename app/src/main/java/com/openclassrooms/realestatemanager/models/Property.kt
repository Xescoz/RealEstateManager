package com.openclassrooms.realestatemanager.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Entity(tableName = "property_table")
data class Property(
        @PrimaryKey
        var id : Int?,
        var date : String,
        var propertyType : String,
        var pointOfInterest: String,
        var price : Int,
        var picture : String,
        var surface : Int,
        var nbOfRooms : String,
        var nbOfBathrooms : String,
        var nbOfBedrooms : String,
        var description : String,
        var sold : Boolean,
        var dateOfSale: String?,
        var city: String,
        var address : String,
        var country : String,
        var apartment : String,
        var postcode : String,
        var photos: @RawValue MutableList<Photo>,
        var locationPicture : String,
        var agent: String): Parcelable