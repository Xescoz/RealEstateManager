package com.openclassrooms.realestatemanager.room

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Property

class SearchPropertyViewModel(private val propertyRepository: PropertyRepository) : ViewModel() {

    fun getPropertyMatchWithPhotos(city: String, numberOfPhotos: Int, pointOfInterest: String, date: String, dateOfSale: String, minPrice: Int, maxPrice: Int, minSize: Int, maxSize: Int): LiveData<List<Property>> {
        return propertyRepository.getPropertyMatchWithPhotos(city, numberOfPhotos, pointOfInterest, date, dateOfSale, minPrice, maxPrice, minSize, maxSize).asLiveData()
    }

    fun getPropertyMatchWithoutPhotos(city: String, pointOfInterest: String, date: String, dateOfSale: String, minPrice: Int, maxPrice: Int, minSize: Int, maxSize: Int): LiveData<List<Property>> {
        return propertyRepository.getPropertyMatchWithoutPhotos(city, pointOfInterest, date, dateOfSale, minPrice, maxPrice, minSize, maxSize).asLiveData()
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