package com.jj.shore.data

import android.content.Context
import com.jj.shore.data.task.OfflineTaskRepository
import com.jj.shore.data.task.TaskRepository

/**
 * REFERENCES
 *
 * https://github.com/google-developer-training/basic-android-kotlin-compose-training-inventory-app/blob/e0773b718f2670e401c039ee965879c5e88ca424/app/src/main/java/com/example/inventory/data/AppContainer.kt
 */

interface AppContainer {
    val tasksRepository: TaskRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val tasksRepository: TaskRepository by lazy {
        OfflineTaskRepository(ShoreDatabase.getDatabase(context).TaskDao())
    }

}
