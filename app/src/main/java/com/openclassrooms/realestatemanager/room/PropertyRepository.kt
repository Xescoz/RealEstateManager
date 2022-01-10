package com.openclassrooms.realestatemanager.room

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.flow.Flow

class PropertyRepository(private val propertyDao: PropertyDao) {

    val allProperty: Flow<List<Property>> = propertyDao.getAllProperty()

    fun updatePhotos(photos: List<Photo>, id: Int){
        propertyDao.updatePhotos(photos,id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(property: Property) {
        propertyDao.insert(property)
    }

}