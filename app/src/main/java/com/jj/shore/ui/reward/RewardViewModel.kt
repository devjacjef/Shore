package com.jj.shore.ui.reward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.shore.data.reward.RewardRepository
import com.jj.shore.data.reward.Reward
import com.jj.shore.ui.task.TaskUiState
import com.jj.shore.ui.task.TaskViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RewardViewModel(private val rewardRepository: RewardRepository) : ViewModel() {
    val rewardUiState: StateFlow<RewardUiState> =
        rewardRepository.getAllRewards().map { RewardUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(RewardViewModel.TIMEOUT_MILLIS),
                initialValue = RewardUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class RewardUiState(val rewardList: List<Reward> = listOf())