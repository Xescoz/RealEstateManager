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
        var id : Int,
        var date : String,
        var propertyType : String,
        var pointOfInterest: String,
        var price : Int,
        var picture : String,
        var surface : String,
        var nbOfRooms : Int,
        var nbOfBathrooms : Int,
        var nbOfBedrooms : Int,
        var description : String,
        var sold : Boolean,
        var dateOfSale: String?,
        var city: String,
        var address : String,
        var country : String,
        var apartment : String,
        var postcode : String,
        var photos: @RawValue List<Photo>,
        var locationPicture : String,
        var agent: String): Parcelable