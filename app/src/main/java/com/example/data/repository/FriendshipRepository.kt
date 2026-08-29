package com.example.data.repository

import com.example.data.local.FriendshipDao
import com.example.data.local.FriendshipSettingsEntity
import com.example.data.local.FutureGoalEntity
import com.example.data.local.MemoryEntity
import kotlinx.coroutines.flow.Flow

class FriendshipRepository(private val dao: FriendshipDao) {

    val allMemories: Flow<List<MemoryEntity>> = dao.getAllMemories()
    val settings: Flow<FriendshipSettingsEntity?> = dao.getSettings()
    val futureGoals: Flow<List<FutureGoalEntity>> = dao.getAllFutureGoals()

    suspend fun addMemory(memory: MemoryEntity) {
        dao.insertMemory(memory)
    }

    suspend fun updateMemory(memory: MemoryEntity) {
        dao.updateMemory(memory)
    }

    suspend fun deleteMemory(memory: MemoryEntity) {
        dao.deleteMemory(memory)
    }

    suspend fun saveSettings(settings: FriendshipSettingsEntity) {
        dao.saveSettings(settings)
    }

    suspend fun addFutureGoal(goal: FutureGoalEntity) {
        dao.insertFutureGoal(goal)
    }

    suspend fun updateFutureGoal(goal: FutureGoalEntity) {
        dao.updateFutureGoal(goal)
    }

    suspend fun deleteFutureGoal(goal: FutureGoalEntity) {
        dao.deleteFutureGoal(goal)
    }
}
