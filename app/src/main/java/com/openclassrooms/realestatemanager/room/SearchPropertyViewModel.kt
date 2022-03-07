package com.openclassrooms.realestatemanager.room

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Property

class SearchPropertyViewModel(private val propertyRepository: PropertyRepository): ViewModel() {
    val allProperty: LiveData<List<Property>> = propertyRepository.allProperty.asLiveData()

    fun getPropertyWhereCity(city: String): LiveData<List<Property>> {
        return propertyRepository.getPropertyWhereCity(city).asLiveData()
    }

    fun getPropertyWhereDate(date: String): LiveData<List<Property>> {
        return propertyRepository.getPropertyWhereDate(date).asLiveData()
    }

    fun getPropertyWhereDateOfSale(dateOfSale: String): LiveData<List<Property>> {
        return propertyRepository.getPropertyWhereDateOfSale(dateOfSale).asLiveData()
    }

    fun getPropertyWhereSizeBetween(minSize: String, maxSize: String): LiveData<List<Property>>{
        return propertyRepository.getPropertyWhereSizeBetween(minSize, maxSize).asLiveData()
    }

    fun getPropertyWherePriceBetween(minPrice: String, maxPrice: String): LiveData<List<Property>>{
        return propertyRepository.getPropertyWherePriceBetween(minPrice, maxPrice).asLiveData()
    }

}

class SearchPropertyViewModelFactory(private val propertyRepository: PropertyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchPropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchPropertyViewModel(propertyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}