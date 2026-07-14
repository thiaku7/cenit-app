package com.example.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class CenitViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = StudyRepository(db.subjectDao())

    private val prefs: SharedPreferences = application.getSharedPreferences("cenit_prefs", Context.MODE_PRIVATE)

    // Data streams
    val allSubjects: StateFlow<List<SubjectProgress>> = repository.allSubjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStudyDays: StateFlow<List<StudyDay>> = repository.allStudyDays
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Controls
    private val _selectedPhase = MutableStateFlow(1)
    val selectedPhase: StateFlow<Int> = _selectedPhase.asStateFlow()

    private val _securityVerified = MutableStateFlow(false)
    val securityVerified: StateFlow<Boolean> = _securityVerified.asStateFlow()

    private val _verificationError = MutableStateFlow<String?>(null)
    val verificationError: StateFlow<String?> = _verificationError.asStateFlow()

    private val _showWelcomeScreen = MutableStateFlow(true)
    val showWelcomeScreen: StateFlow<Boolean> = _showWelcomeScreen.asStateFlow()

    // Config options
    private val _readingMode = MutableStateFlow(false)
    val readingMode: StateFlow<Boolean> = _readingMode.asStateFlow()

    private val _remindersEnabled = MutableStateFlow(true)
    val remindersEnabled: StateFlow<Boolean> = _remindersEnabled.asStateFlow()

    private val _reminderHour = MutableStateFlow(19)
    val reminderHour: StateFlow<Int> = _reminderHour.asStateFlow()

    private val _reminderMinute = MutableStateFlow(0)
    val reminderMinute: StateFlow<Int> = _reminderMinute.asStateFlow()

    // Streaks
    private val _studyStreak = MutableStateFlow(0)
    val studyStreak: StateFlow<Int> = _studyStreak.asStateFlow()

    // Active Pomodoro States
    private val _pomoTimerActive = MutableStateFlow(false)
    val pomoTimerActive: StateFlow<Boolean> = _pomoTimerActive.asStateFlow()

    private val _pomoTimeRemaining = MutableStateFlow(1500L)
    val pomoTimeRemaining: StateFlow<Long> = _pomoTimeRemaining.asStateFlow()

    private val _pomoIsStudySession = MutableStateFlow(true)
    val pomoIsStudySession: StateFlow<Boolean> = _pomoIsStudySession.asStateFlow()

    private val _pomoConfigStudyMinutes = MutableStateFlow(25)
    val pomoConfigStudyMinutes: StateFlow<Int> = _pomoConfigStudyMinutes.asStateFlow()

    private val _pomoConfigRestMinutes = MutableStateFlow(5)
    val pomoConfigRestMinutes: StateFlow<Int> = _pomoConfigRestMinutes.asStateFlow()

    private val _selectedSubjectForPomo = MutableStateFlow<SubjectProgress?>(null)
    val selectedSubjectForPomo: StateFlow<SubjectProgress?> = _selectedSubjectForPomo.asStateFlow()

    // Daily Quote
    private val _dailyQuote = MutableStateFlow("")
    val dailyQuote: StateFlow<String> = _dailyQuote.asStateFlow()

    // Firestore
    private val _firestoreUserId = MutableStateFlow<String?>(null)
    val firestoreUserId: StateFlow<String?> = _firestoreUserId.asStateFlow()

    private var countdownTimer: CountDownTimer? = null

    private val quotesList = listOf(
        "\"La consistencia vence al talento. Estudiar de forma constante todos los días es el origen del poder real.\"",
        "\"No avances si no entendiste las bases. El conocimiento es una pirámide y debe edificarse en terreno firme.\"",
        "\"La aplicación real es obligatoria. El conocimiento sin acción consecuente es mero pasatiempo intelectual.\"",
        "\"La disciplina no es una ráfaga de motivación esporádica — es un sistema neurológico estructurado.\"",
        "\"El ego es tu enemigo en todo aprendizaje. Acércate a cada materia con la mente del principiante.\"",
        "\"Protege tu bloque diario de estudio profundo. Sin notificaciones, sin interrupciones, enfoque total.\"",
        "\"El largo plazo siempre triunfa. La disciplina sostenida genera retornos que parecen mágicos.\"",
        "\"Tu cuerpo es el hardware de tu mente. El sueño reparador, la nutrición y la fuerza no son negociables.\"",
        "\"La racha de hoy es el ladrillo esencial para la obra de tu vida entera. No rompas la cadena.\"",
        "\"Aprender es reprogramar tu propio destino. Cada semana completada es un paso hacia la maestría total.\""
    )

    init {
        viewModelScope.launch {
            repository.initializeSubjectsIfNeeded()
            loadSavedStates()
            calculateStreakThresholds()
            selectRandomQuote()
        }
    }

    private fun loadSavedStates() {
        val verified = prefs.getBoolean("security_verified", false)
        _securityVerified.value = verified

        val welcomeDismissed = prefs.getBoolean("welcome_dismissed", false)
        _showWelcomeScreen.value = !welcomeDismissed

        _readingMode.value = prefs.getBoolean("reading_mode", false)
        _remindersEnabled.value = prefs.getBoolean("reminders_enabled", true)
        _reminderHour.value = prefs.getInt("reminder_hour", 19)
        _reminderMinute.value = prefs.getInt("reminder_minute", 0)

        _pomoConfigStudyMinutes.value = prefs.getInt("pomo_study_min", 25)
        _pomoConfigRestMinutes.value = prefs.getInt("pomo_rest_min", 5)
        resetTimerToConfig()

        _studyStreak.value = prefs.getInt("current_streak", 0)
    }

    private fun selectRandomQuote() {
        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val index = dayOfYear % quotesList.size
        _dailyQuote.value = quotesList[index]
    }

    fun verifyCodeDirectly(code: String) {
        viewModelScope.launch {
            prefs.edit().apply {
                putString("saved_access_code", code)
                putBoolean("security_verified", true)
                apply()
            }
            _securityVerified.value = true
        }
    }

    fun clearVerificationError() {
        _verificationError.value = null
    }

    fun setVerificationError(error: String) {
        _verificationError.value = error
    }

    fun logout() {
        viewModelScope.launch {
            prefs.edit().apply {
                remove("saved_access_code")
                putBoolean("security_verified", false)
                apply()
            }
            _securityVerified.value = false
        }
    }

    fun dismissWelcomeScreen() {
        viewModelScope.launch {
            prefs.edit().putBoolean("welcome_dismissed", true).apply()
            _showWelcomeScreen.value = false
        }
    }

    fun showWelcomeAgain() {
        _showWelcomeScreen.value = true
    }

    fun setPhase(phase: Int) {
        _selectedPhase.value = phase
    }

    fun toggleReadingMode() {
        val newValue = !_readingMode.value
        _readingMode.value = newValue
        prefs.edit().putBoolean("reading_mode", newValue).apply()
    }

    fun toggleReminders() {
        val newValue = !_remindersEnabled.value
        _remindersEnabled.value = newValue
        prefs.edit().putBoolean("reminders_enabled", newValue).apply()
    }

    fun setReminderTime(hour: Int, minute: Int) {
        _reminderHour.value = hour
        _reminderMinute.value = minute
        prefs.edit().apply {
            putInt("reminder_hour", hour)
            putInt("reminder_minute", minute)
            apply()
        }
    }

    fun updatePomodoroConfigs(studyMin: Int, restMin: Int) {
        _pomoConfigStudyMinutes.value = studyMin
        _pomoConfigRestMinutes.value = restMin
        prefs.edit().apply {
            putInt("pomo_study_min", studyMin)
            putInt("pomo_rest_min", restMin)
            apply()
        }
        if (!_pomoTimerActive.value) {
            resetTimerToConfig()
        }
    }

    fun selectSubjectForPomo(subject: SubjectProgress?) {
        _selectedSubjectForPomo.value = subject
    }

    fun startTimer() {
        if (_pomoTimerActive.value) return
        _pomoTimerActive.value = true

        countdownTimer = object : CountDownTimer(_pomoTimeRemaining.value * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _pomoTimeRemaining.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    fun pauseTimer() {
        countdownTimer?.cancel()
        _pomoTimerActive.value = false
    }

    fun resetTimerToConfig() {
        countdownTimer?.cancel()
        _pomoTimerActive.value = false
        _pomoTimeRemaining.value = if (_pomoIsStudySession.value) {
            _pomoConfigStudyMinutes.value * 60L
        } else {
            _pomoConfigRestMinutes.value * 60L
        }
    }

    private fun onTimerFinished() {
        _pomoTimerActive.value = false
        val finishedSessionWasStudy = _pomoIsStudySession.value

        if (finishedSessionWasStudy) {
            val studyMinutes = _pomoConfigStudyMinutes.value
            addStudiedMinutesToCalendar(studyMinutes)

            _selectedSubjectForPomo.value?.let { subject ->
                viewModelScope.launch {
                    val updated = subject.copy(timeSpentSeconds = subject.timeSpentSeconds + (studyMinutes * 60))
                    repository.updateSubject(updated)
                    syncSubjectToFirestore(updated)
                    _selectedSubjectForPomo.value = updated
                }
            }

            incrementStreakProgress()
        }

        _pomoIsStudySession.value = !finishedSessionWasStudy
        resetTimerToConfig()
    }

    private fun addStudiedMinutesToCalendar(minutes: Int) {
        val todayStr = getTodayDateString()
        viewModelScope.launch {
            repository.insertOrUpdateStudyDay(todayStr, minutes)
        }
    }

    private fun incrementStreakProgress() {
        val todayStr = getTodayDateString()
        val lastStudyDate = prefs.getString("last_study_date", "") ?: ""
        val currentStreak = prefs.getInt("current_streak", 0)

        val newStreak = if (lastStudyDate == todayStr) {
            currentStreak
        } else if (lastStudyDate == getYesterdayDateString()) {
            currentStreak + 1
        } else {
            1
        }

        prefs.edit().apply {
            putString("last_study_date", todayStr)
            putInt("current_streak", newStreak)
            apply()
        }
        _studyStreak.value = newStreak
        syncStreakToFirestore()
    }

    private fun calculateStreakThresholds() {
        val todayStr = getTodayDateString()
        val lastStudyDate = prefs.getString("last_study_date", "") ?: ""
        val currentStreak = prefs.getInt("current_streak", 0)

        if (lastStudyDate.isNotEmpty() && lastStudyDate != todayStr && lastStudyDate != getYesterdayDateString()) {
            prefs.edit().putInt("current_streak", 0).apply()
            _studyStreak.value = 0
        }
    }

    fun updateSubjectStatus(subject: SubjectProgress, newStatus: String) {
        viewModelScope.launch {
            val updated = subject.copy(status = newStatus)
            repository.updateSubject(updated)
            syncSubjectToFirestore(updated)
            if (_selectedSubjectForPomo.value?.id == subject.id) {
                _selectedSubjectForPomo.value = updated
            }
        }
    }

    fun updateSubjectNotes(subject: SubjectProgress, newNotes: String) {
        viewModelScope.launch {
            val updated = subject.copy(notes = newNotes)
            repository.updateSubject(updated)
            syncSubjectToFirestore(updated)
            if (_selectedSubjectForPomo.value?.id == subject.id) {
                _selectedSubjectForPomo.value = updated
            }
        }
    }

    fun resetAllAppProgress() {
        viewModelScope.launch {
            repository.resetAllProgress()
            prefs.edit().apply {
                remove("last_study_date")
                putInt("current_streak", 0)
                apply()
            }
            _studyStreak.value = 0
            _selectedSubjectForPomo.value = null
            resetTimerToConfig()
        }
    }

    fun setFirestoreUserId(userId: String) {
        _firestoreUserId.value = userId
        prefs.edit().putString("firestore_user_id", userId).apply()
    }

    fun loadFirestoreUserId() {
        val userId = prefs.getString("firestore_user_id", null)
        _firestoreUserId.value = userId
    }

    fun syncSubjectToFirestore(subject: SubjectProgress) {
        val userId = _firestoreUserId.value ?: return
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        val data = mapOf(
            "titulo" to subject.name,
            "fase" to subject.phase,
            "status" to subject.status,
            "progreso" to ((subject.timeSpentSeconds / 3600.0) * 100 / 40).toInt().coerceIn(0, 100),
            "apuntes" to subject.notes,
            "timeSpentSeconds" to subject.timeSpentSeconds
        )

        db.collection("users").document(userId)
            .collection("materias").document(subject.id.toString())
            .set(data, com.google.firebase.firestore.SetOptions.merge())
            .addOnFailureListener { e -> Log.e("CENIT", "Error sync subject: ${e.message}") }
    }

    fun syncStreakToFirestore() {
        val userId = _firestoreUserId.value ?: return
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        val data = mapOf(
            "strikeCount" to _studyStreak.value,
            "lastStudyDate" to getTodayDateString(),
            "totalDaysStudied" to _studyStreak.value
        )

        db.collection("users").document(userId)
            .set(data, com.google.firebase.firestore.SetOptions.merge())
            .addOnFailureListener { e -> Log.e("CENIT", "Error sync streak: ${e.message}") }
    }

    fun loadDataFromFirestore() {
        val userId = _firestoreUserId.value ?: return
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        viewModelScope.launch {
            try {
                val snapshot = db.collection("users").document(userId)
                    .collection("materias").get().await()

                snapshot.documents.forEach { doc ->
                    val status = doc.getString("status") ?: "PENDIENTE"
                    val notes = doc.getString("apuntes") ?: ""
                    val timeSpent = doc.getLong("timeSpentSeconds") ?: 0L

                    val subject = allSubjects.value.find { it.id.toString() == doc.id }
                    subject?.let {
                        val updated = it.copy(status = status, notes = notes, timeSpentSeconds = timeSpent)
                        repository.updateSubject(updated)
                    }
                }
            } catch (e: Exception) {
                Log.e("CENIT", "Error loading subjects: ${e.message}")
            }
        }
    }

    fun getTodayDateString(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    fun getYesterdayDateString(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
    }
}
