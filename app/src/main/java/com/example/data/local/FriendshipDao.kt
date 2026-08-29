package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendshipDao {
    // Memories
    @Query("SELECT * FROM memories ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity): Long

    @Update
    suspend fun updateMemory(memory: MemoryEntity)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)

    @Query("DELETE FROM memories WHERE id = :id")
    suspend fun deleteMemoryById(id: Long)

    // Settings & Shared Messages
    @Query("SELECT * FROM friendship_settings WHERE id = 1")
    fun getSettings(): Flow<FriendshipSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: FriendshipSettingsEntity)

    // Future Goals
    @Query("SELECT * FROM future_goals ORDER BY id ASC")
    fun getAllFutureGoals(): Flow<List<FutureGoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFutureGoal(goal: FutureGoalEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFutureGoals(goals: List<FutureGoalEntity>)

    @Update
    suspend fun updateFutureGoal(goal: FutureGoalEntity)

    @Delete
    suspend fun deleteFutureGoal(goal: FutureGoalEntity)
}
