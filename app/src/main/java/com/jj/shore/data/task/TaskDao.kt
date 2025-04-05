package com.jj.shore.data.task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * REFERENCES
 *
 * https://developer.android.com/training/data-storage/room
 */

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id IN (:taskId)")
    fun getTask(taskId: Int): Flow<Task>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    fun update(task: Task)
}