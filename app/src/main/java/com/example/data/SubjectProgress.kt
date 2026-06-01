package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject_progress")
data class SubjectProgress(
    @PrimaryKey val id: String, // e.g., "01-01" through "06-06"
    val phase: Int,            // 1 to 6
    val category: String,      // "Mental", "Sistemas", etc.
    val name: String,
    val description: String,
    val topics: String,        // Semicolon-separated topics
    val durationText: String,  // e.g., "3 semanas"
    val status: String,        // "PENDING", "IN_PROGRESS", "COMPLETED"
    val notes: String = "",
    val timeSpentSeconds: Long = 0L
)
