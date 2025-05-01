package com.jj.shore.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * REFERENCES
 *
 * https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
 */

//@Database(entities = [Task::class], version = 1, exportSchema = false)
//abstract class ShoreDatabase : RoomDatabase() {
//    abstract fun TaskDao(): TaskDao
//
//    companion object {
//        @Volatile
//        private var Instance: ShoreDatabase? = null
//
//        fun getDatabase(context: Context): ShoreDatabase {
//            return Instance ?: synchronized(this) {
//                Room.databaseBuilder(context, ShoreDatabase::class.java, "tasks")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    .also { Instance = it }
//            }
//        }
//    }
//}