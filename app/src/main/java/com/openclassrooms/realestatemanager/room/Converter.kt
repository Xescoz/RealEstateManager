package com.openclassrooms.realestatemanager.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openclassrooms.realestatemanager.models.Photo

class Converter {
    private var gson = Gson()

    @TypeConverter
    fun foodItemToString(photosItem: List<Photo>): String {
        return gson.toJson(photosItem)
    }

    @TypeConverter
    fun stringToFoodItem(data: String): List<Photo> {
        val listType = object : TypeToken<List<Photo>>() {
        }.type
        return gson.fromJson(data, listType)
    }
}