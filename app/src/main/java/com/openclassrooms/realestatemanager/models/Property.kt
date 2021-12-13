package com.openclassrooms.realestatemanager.models

class Property(
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
        var locationPicture : String,
        var agent: Agent) {
}