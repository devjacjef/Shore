package com.jj.shore.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jj.shore.ShoreApplication
import com.jj.shore.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(shoreApplication().container.tasksRepository)
        }
    }
}

fun CreationExtras.shoreApplication(): ShoreApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShoreApplication)