package com.openclassrooms.realestatemanager.room

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.flow.Flow

class PropertyRepository(private val propertyDao: PropertyDao) {

    val allProperty: Flow<List<Property>> = propertyDao.getAllProperty()

    suspend fun updateProperty(property: Property){
        propertyDao.updateProperty(property)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(property: Property) {
        propertyDao.insert(property)
    }

    fun getPropertyMatch(city: String, numberOfPhotos: Int, pointOfInterest: String, date: String, dateOfSale: String, minPrice: Int, maxPrice: Int, minSize: Int, maxSize: Int):Flow<List<Property>> {
        return propertyDao.getPropertyMatch(city, numberOfPhotos,pointOfInterest,date,dateOfSale,minPrice, maxPrice,minSize, maxSize)
    }

}