package com.openclassrooms.realestatemanager.room

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.models.Agent
import kotlinx.coroutines.flow.Flow

class AgentRepository(private val agentDao: AgentDao) {

    val allAgent: Flow<List<Agent>> = agentDao.getAllAgent()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(agent: Agent) {
        agentDao.insert(agent)
    }
}