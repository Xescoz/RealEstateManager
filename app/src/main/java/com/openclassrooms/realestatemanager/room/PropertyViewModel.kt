package com.openclassrooms.realestatemanager.room

import android.util.Log
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.models.Agent
import com.openclassrooms.realestatemanager.models.Property
import kotlinx.coroutines.launch


class PropertyViewModel(private val propertyRepository: PropertyRepository, agentRepository: AgentRepository): ViewModel() {

    val allProperty: LiveData<List<Property>> = propertyRepository.allProperty.asLiveData()

    val allAgent: LiveData<List<Agent>> = agentRepository.allAgent.asLiveData()

    fun insert(property: Property) = viewModelScope.launch {
        propertyRepository.insert(property)
    }

    fun updateProperty(property: Property) = viewModelScope.launch{
        propertyRepository.updateProperty(property)
    }

    fun getAllProperties():LiveData<List<Property>>{
        Log.v("Ping fragment",allProperty.value?.get(0)?.price.toString())
        return allProperty
    }

}


class PropertyViewModelFactory(private val propertyRepository: PropertyRepository,private val agentRepository: AgentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PropertyViewModel(propertyRepository,agentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
