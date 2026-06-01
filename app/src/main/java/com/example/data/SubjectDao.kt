package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subject_progress ORDER BY id ASC")
    fun getAllSubjects(): Flow<List<SubjectProgress>>

    @Query("SELECT * FROM subject_progress WHERE phase = :phase ORDER BY id ASC")
    fun getSubjectsByPhase(phase: Int): Flow<List<SubjectProgress>>

    @Query("SELECT * FROM subject_progress WHERE id = :id LIMIT 1")
    suspend fun getSubjectById(id: String): SubjectProgress?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(subjects: List<SubjectProgress>)

    @Update
    suspend fun updateSubject(subject: SubjectProgress)

    @Query("DELETE FROM subject_progress")
    suspend fun clearSubjects()

    // Study Days queries
    @Query("SELECT * FROM study_days ORDER BY dateString ASC")
    fun getAllStudyDays(): Flow<List<StudyDay>>

    @Query("SELECT * FROM study_days WHERE dateString = :dateString LIMIT 1")
    suspend fun getStudyDay(dateString: String): StudyDay?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateStudyDay(day: StudyDay)

    @Query("DELETE FROM study_days")
    suspend fun clearStudyDays()
}
