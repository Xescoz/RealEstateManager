package com.openclassrooms.realestatemanager.room

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PropertyApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { PropertyRoomDatabase.getDatabase(this, applicationScope) }
    val propertyRepository by lazy { PropertyRepository(database.propertyDao()) }
    val agentRepository by lazy { AgentRepository(database.agentDao()) }
}