package com.jj.shore.data.reward

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * REFERENCES
 *
 * https://github.com/Mehrpouya/CMP309/blob/main/PracticeTwo/app/src/main/java/com/example/practicetwo/MoviesDatabase.kt
 *
 */

@Entity(tableName = "rewards")
data class Reward(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String
)