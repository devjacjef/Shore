package com.jj.shore.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jj.shore.data.reward.Reward
import com.jj.shore.data.reward.RewardDao
import com.jj.shore.data.task.Task
import com.jj.shore.data.task.TaskDao

/**
 * REFERENCES
 *
 * https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */

@Database(entities = [Task::class, Reward::class], version = 2, exportSchema = false)
abstract class ShoreDatabase : RoomDatabase() {
    abstract fun TaskDao(): TaskDao
    abstract fun RewardDao(): RewardDao

    companion object {
        @Volatile
        private var Instance: ShoreDatabase? = null

        fun getDatabase(context: Context): ShoreDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ShoreDatabase::class.java, "shore_db") // <- Unified DB name
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}