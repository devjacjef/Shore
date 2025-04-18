package com.jj.shore.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jj.shore.ShoreApplication
import com.jj.shore.ui.home.HomeViewModel
import com.jj.shore.ui.reward.RewardViewModel
import com.jj.shore.ui.task.TaskViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TaskViewModel(shoreApplication().container.tasksRepository)
        }
        initializer {
            RewardViewModel(shoreApplication().container.rewardRepository)
        }
    }
}

fun CreationExtras.shoreApplication(): ShoreApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShoreApplication)