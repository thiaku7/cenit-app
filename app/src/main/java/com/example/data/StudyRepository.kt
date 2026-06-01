package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class StudyRepository(private val subjectDao: SubjectDao) {
    val allSubjects: Flow<List<SubjectProgress>> = subjectDao.getAllSubjects()
    val allStudyDays: Flow<List<StudyDay>> = subjectDao.getAllStudyDays()

    suspend fun getSubjectById(id: String): SubjectProgress? {
        return subjectDao.getSubjectById(id)
    }

    suspend fun updateSubject(subject: SubjectProgress) {
        subjectDao.updateSubject(subject)
    }

    suspend fun insertOrUpdateStudyDay(dateString: String, addMinutes: Int) {
        val existing = subjectDao.getStudyDay(dateString)
        val newMinutes = (existing?.minutesStudied ?: 0) + addMinutes
        subjectDao.insertOrUpdateStudyDay(StudyDay(dateString, newMinutes))
    }

    suspend fun initializeSubjectsIfNeeded() {
        val existingList = allSubjects.first()
        if (existingList.size < 42) {
            subjectDao.clearSubjects()
            subjectDao.insertAll(CenitSubjects.items)
        }
    }

    suspend fun resetAllProgress() {
        subjectDao.clearSubjects()
        subjectDao.clearStudyDays()
        subjectDao.insertAll(CenitSubjects.items)
    }
}
