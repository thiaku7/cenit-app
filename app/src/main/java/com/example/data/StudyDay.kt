package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_days")
data class StudyDay(
    @PrimaryKey val dateString: String, // "YYYY-MM-DD" en UTC/Local
    val minutesStudied: Int
)
