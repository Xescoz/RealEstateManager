package com.openclassrooms.realestatemanager.room

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Photo
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PropertyViewModel(private val repository: PropertyRepository): ViewModel() {

    val allProperty: LiveData<List<Property>> = repository.allProperty.asLiveData()

    fun insert(property: Property) = viewModelScope.launch {
        repository.insert(property)
    }

    fun updateProperty(property: Property) = viewModelScope.launch{
        repository.updateProperty(property)
    }

}

class PropertyViewModelFactory(private val repository: PropertyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropertyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
