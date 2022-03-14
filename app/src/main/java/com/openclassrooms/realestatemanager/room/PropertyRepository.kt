package com.openclassrooms.realestatemanager.room

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

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

    fun getPropertyWherePointOfInterest(pointOfInterest: String): Flow<List<Property>>{
        return propertyDao.getPropertyWherePointOfInterest(pointOfInterest)
    }

    fun getPropertyWhereNumberOfPhotos(numberOfPhotos: Int): Flow<List<Property>>{
        return propertyDao.getPropertyWhereNumberOfPhotos(numberOfPhotos)
    }

    fun getPropertyWhereCity(city: String):Flow<List<Property>> {
        return propertyDao.getPropertyWhereCity(city)
    }

    fun getPropertyWhereDate(date: String): Flow<List<Property>>{
        return propertyDao.getPropertyWhereDate(date)
    }
    fun getPropertyWhereDateOfSale(dateOfSale: String): Flow<List<Property>>{
        return propertyDao.getPropertyWhereDateOfSale(dateOfSale)
    }

    fun getPropertyWhereSizeBetween(minSize: Int, maxSize: Int): Flow<List<Property>>{
        return propertyDao.getPropertyWhereSizeBetween(minSize, maxSize)
    }

    fun getPropertyWherePriceBetween(minPrice: Int, maxPrice: Int): Flow<List<Property>>{
        return propertyDao.getPropertyWherePriceBetween(minPrice, maxPrice)
    }

}