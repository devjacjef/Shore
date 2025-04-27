package com.jj.shore.data

import android.accounts.Account
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.jj.shore.data.auth.AuthRemoteDataSource
import com.jj.shore.data.auth.AuthRepository
import com.jj.shore.data.task.OfflineTaskRepository
import com.jj.shore.data.task.TaskRepository

/**
 * REFERENCES
 *
 * https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app/blob/e0773b718f2670e401c039ee965879c5e88ca424/app/src/main/java/com/example/inventory/data/AppContainer.kt
 */

/**
 * AppContainer Interface
 */
interface AppContainer {
    val tasksRepository: TaskRepository
    val authRepository: AuthRepository
}

/**
 *
 */
class AppDataContainer(private val context: Context) : AppContainer {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val authRemoteDataSource: AuthRemoteDataSource by lazy {
        AuthRemoteDataSource(firebaseAuth)
    }

    override val tasksRepository: TaskRepository by lazy {
        OfflineTaskRepository(ShoreDatabase.getDatabase(context).TaskDao())
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepository(authRemoteDataSource)
    }

}
