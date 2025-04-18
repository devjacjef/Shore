package com.jj.shore.data.reward

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.jj.shore.data.reward.Reward
import kotlinx.coroutines.flow.Flow

/**
 * REFERENCES
 *
 * https://developer.android.com/training/data-storage/room
 */

@Dao
interface RewardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reward: Reward)

    @Delete
    suspend fun delete(reward: Reward)

    @Query("DELETE FROM rewards WHERE id IN (:rewardIds)")
    suspend fun deleteRewardsByIds(rewardIds: List<Int>)

    @Query("DELETE FROM rewards")
    suspend fun deleteAllRewards()

    @Update
    suspend fun update(reward: Reward)

    @Upsert
    suspend fun upsert(reward: Reward)

    @Query("SELECT * FROM rewards")
    fun getAllTasks(): Flow<List<Reward>>

    @Query("SELECT * FROM rewards WHERE id = :rewardId")
    fun getTaskById(rewardId: Int): Flow<Reward>
}