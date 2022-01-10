package com.openclassrooms.realestatemanager.room

import androidx.room.*
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: Property)

    @Query("SELECT * FROM property_table")
    fun getAllProperty(): Flow<List<Property>>

    @Query("DELETE FROM property_table")
    suspend fun deleteAll()

    @Query("UPDATE property_table SET photos = :photos WHERE id = :id")
    fun updatePhotos(photos: List<Photo>, id: Int)

}