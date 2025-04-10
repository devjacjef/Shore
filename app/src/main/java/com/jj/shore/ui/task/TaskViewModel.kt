package com.jj.shore.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.shore.Task
import com.jj.shore.data.task.TaskRepository
import com.jj.shore.ui.home.HomeUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val taskUiState: StateFlow<HomeUiState> =
        taskRepository.getAllTasksStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TaskViewModel.TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    fun insertTask(task: com.jj.shore.data.task.Task) {
        viewModelScope.launch {
            taskRepository.insert(task) // You can call methods here!
        }
    }

    fun clearAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TaskUiState(val taskList: List<Task> = listOf())