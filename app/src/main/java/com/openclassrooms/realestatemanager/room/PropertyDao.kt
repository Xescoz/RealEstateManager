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

    @Update
    suspend fun updateProperty(property: Property)

    @Query("SELECT * FROM property_table WHERE city = :city")
    fun getPropertyWhereCity(city: String): Flow<List<Property>>

    @Query("SELECT * FROM property_table WHERE date = :date")
    fun getPropertyWhereDate(date: String): Flow<List<Property>>

    @Query("SELECT * FROM property_table WHERE dateOfSale = :dateOfSale")
    fun getPropertyWhereDateOfSale(dateOfSale: String): Flow<List<Property>>

    @Query("SELECT * FROM property_table WHERE surface BETWEEN :minSize AND :maxSize")
    fun getPropertyWhereSizeBetween(minSize: String, maxSize: String): Flow<List<Property>>

    @Query("SELECT * FROM property_table WHERE price BETWEEN :minPrice AND :maxPrice")
    fun getPropertyWherePriceBetween(minPrice: String, maxPrice: String): Flow<List<Property>>
}