package com.jj.shore.ui.home

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.data.task.Task
import com.jj.shore.data.task.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// FIXME: Move logic to TaskViewModel
class HomeViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        taskRepository.getAllTasksStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    // FIXME: MOVE TO TASKVIEWMODEL
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insert(task) // You can call methods here!
        }
    }

    // FIXME: MOVE TO TASKVIEWMODEL
    fun clearAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val taskList: List<Task> = listOf())