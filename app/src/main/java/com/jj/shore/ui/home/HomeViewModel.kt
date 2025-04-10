package com.jj.shore.ui.home

import androidx.lifecycle.ViewModel
import com.jj.shore.data.task.Task
import com.jj.shore.data.task.TaskRepository

class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel() {
//    val homeUiState: StateFlow<HomeUiState> =
//        taskRepository.getAllTasksStream().map { HomeUiState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = HomeUiState()
//            )
//
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
}

data class HomeUiState(val taskList: List<Task> = listOf())