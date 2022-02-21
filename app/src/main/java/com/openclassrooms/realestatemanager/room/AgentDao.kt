package com.openclassrooms.realestatemanager.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.models.Agent
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(agent: Agent)

    @Query("SELECT * FROM agent_table")
    fun getAllAgent(): Flow<List<Agent>>
}