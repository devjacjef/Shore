package com.jj.shore.ui.reward

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.data.reward.Reward
import com.jj.shore.ui.AppViewModelProvider
import com.jj.shore.ui.task.TaskViewModel

@Composable
fun RewardScreen(
    viewModel: RewardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val rewardUiState: RewardUiState by viewModel.rewardUiState.collectAsState()
    val rewardList = rewardUiState.rewardList

    if(rewardList.isEmpty()) {
        Box(Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Text(text = "No Rewards have been added.", color = Color.Gray)
        }
    }


}