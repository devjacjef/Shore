package com.jj.shore.data.reward

import com.jj.shore.data.reward.RewardRepository
import com.jj.shore.data.reward.Reward
import com.jj.shore.data.reward.RewardDao
import kotlinx.coroutines.flow.Flow

class OfflineRewardRepository(private val rewardDao: RewardDao) : RewardRepository {
    override fun getAllRewards(): Flow<List<Reward>> = rewardDao.getAllTasks()

    override fun getRewardStream(id: Int): Flow<Reward?> = rewardDao.getTaskById(id)

    override suspend fun insert(reward: Reward) = rewardDao.insert(reward)

    override suspend fun delete(reward: Reward) = rewardDao.delete(reward)

    override suspend fun update(reward: Reward) = rewardDao.update(reward)

    override suspend fun deleteAllRewards() {
        rewardDao.deleteAllRewards()  // Call to delete all tasks
    }

    override suspend fun deleteAllRewardsById(rewardIds: List<Int>) {
        rewardDao.deleteRewardsByIds(rewardIds)
    }
}