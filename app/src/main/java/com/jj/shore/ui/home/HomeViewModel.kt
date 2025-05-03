package com.jj.shore.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.shore.data.auth.AuthRepository
import com.jj.shore.data.task.TaskRepository
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _userEmail = mutableStateOf<String?>(null)
    val userEmail: State<String?> = _userEmail

    private val _outstandingTasksCount = mutableStateOf<Int?>(null)
    val outstandingTasksCount: State<Int?> = _outstandingTasksCount

    val userIdStateFlow: StateFlow<String?> = authRepository.currentUserIdFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        loadUserData()
    }

    // Add this method to handle user data loading
    private fun loadUserData() {
        viewModelScope.launch {
            // Use currentUserFlow instead of directly accessing currentUser
            authRepository.currentUserIdFlow.collectLatest { userId ->
                val user = authRepository.currentUser
                _userEmail.value = user?.email

                if (userId != null) {
                    observeOutstandingTaskCount(userId)
                }
            }
        }
    }

    private fun observeOutstandingTaskCount(userId: String) {
        viewModelScope.launch {
            taskRepository.getOutstandingTaskCountFlow(userId).collectLatest { count ->
                _outstandingTasksCount.value = count
            }
        }
    }
}