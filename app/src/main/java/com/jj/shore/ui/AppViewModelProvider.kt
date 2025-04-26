package com.jj.shore.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jj.shore.ShoreApplication
import com.jj.shore.ui.home.HomeViewModel
import com.jj.shore.ui.login.LoginViewModel
import com.jj.shore.ui.task.TaskViewModel

/**
 * Handles Manual Dependency Injection
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TaskViewModel(shoreApplication().container.tasksRepository)
        }

        initializer {
            val app = shoreApplication()
            val accountService = app.container.accountService
            LoginViewModel(accountService)
        }
    }
}

fun CreationExtras.shoreApplication(): ShoreApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShoreApplication)