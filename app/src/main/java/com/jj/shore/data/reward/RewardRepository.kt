package com.jj.shore.data.reward

import com.jj.shore.data.reward.Reward
import kotlinx.coroutines.flow.Flow

interface RewardRepository {
    fun getAllRewards(): Flow<List<Reward>>

    fun getRewardStream(id: Int): Flow<Reward?>

    suspend fun insert(reward: Reward)

    suspend fun delete(reward: Reward)

    suspend fun deleteAllRewards()

    suspend fun deleteAllRewardsById(rewardIds: List<Int>)

    suspend fun update(reward: Reward)


}