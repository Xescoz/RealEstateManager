package com.openclassrooms.realestatemanager.room

import androidx.room.*
import com.google.android.gms.maps.model.PointOfInterest
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

    @Update
    suspend fun updateProperty(property: Property)

    @Query("SELECT * FROM property_table " +
            "WHERE city LIKE '%' || :city || '%' " +
            "AND numberOfPhotos = :numberOfPhotos " +
            "AND pointOfInterest LIKE '%' || :pointOfInterest || '%' " +
            "AND date LIKE '%' || :date || '%' " +
            "AND dateOfSale LIKE '%' || :dateOfSale || '%' " +
            "AND price BETWEEN :minPrice AND :maxPrice " +
            "AND surface BETWEEN :minSize AND :maxSize")
    fun getPropertyMatchWithPhotos(city: String, numberOfPhotos: Int, pointOfInterest: String, date: String, dateOfSale: String, minPrice: Int, maxPrice: Int, minSize: Int, maxSize: Int): Flow<List<Property>>

    @Query("SELECT * FROM property_table " +
            "WHERE city LIKE '%' || :city || '%' " +
            "AND pointOfInterest LIKE '%' || :pointOfInterest || '%' " +
            "AND date LIKE '%' || :date || '%' " +
            "AND dateOfSale LIKE '%' || :dateOfSale || '%' " +
            "AND price BETWEEN :minPrice AND :maxPrice " +
            "AND surface BETWEEN :minSize AND :maxSize")
    fun getPropertyMatchWithoutPhotos(city: String, pointOfInterest: String, date: String, dateOfSale: String, minPrice: Int, maxPrice: Int, minSize: Int, maxSize: Int): Flow<List<Property>>
}