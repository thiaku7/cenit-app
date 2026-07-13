package com.example.security

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseSecurityService(private val context: Context) {
    val deviceId: String by lazy {
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "dispositivo_desconocido"
    }

    interface SecurityCallback {
        fun onSuccess()
        fun onError(message: String)
    }

    fun verifyAccessCode(code: String, callback: SecurityCallback) {
        val trimmedCode = code.trim()
        if (trimmedCode.isEmpty()) {
            callback.onError("El código de acceso no puede estar vacío.")
            return
        }

        try {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("codigos").document(trimmedCode)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val enUso = document.getBoolean("enUso") ?: false
                        val dispositivoId = document.getString("dispositivoId") ?: ""
                        when {
                            !enUso && dispositivoId.isEmpty() -> {
                                // Crear estructura usuario PRIMERO
                                crearEstructuraUsuario(deviceId, {
                                    // Luego marcar código como usado
                                    docRef.update(
                                        mapOf(
                                            "enUso" to true,
                                            "dispositivoId" to deviceId
                                        )
                                    )
                                        .addOnSuccessListener { callback.onSuccess() }
                                        .addOnFailureListener { e ->
                                            Log.e("CENIT", "Error al vincular: ${e.message}")
                                            callback.onError("Error al registrar dispositivo.")
                                        }
                                }, { error ->
                                    callback.onError(error)
                                })
                            }
                            dispositivoId == deviceId -> {
                                callback.onSuccess()
                            }
                            else -> {
                                callback.onError("Este código ya está en uso en otro dispositivo.")
                            }
                        }
                    } else {
                        callback.onError("Código inválido.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("CENIT", "Error de Firestore: ${exception.message}")
                    callback.onError("Error de conexión.")
                }
        } catch (e: Exception) {
            Log.e("CENIT", "Firebase no inicializado: ${e.message}")
            callback.onError("Error: ${e.message}")
        }
    }

    private fun crearEstructuraUsuario(userId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)

        val profileData = mapOf(
            "strikeCount" to 0,
            "lastStudyDate" to null,
            "totalDaysStudied" to 0
        )

        userRef.set(profileData)
            .addOnSuccessListener {
                userRef.collection("materias").document("init").set(
                    mapOf(
                        "titulo" to "Plantilla",
                        "status" to "PENDIENTE",
                        "progreso" to 0
                    )
                )
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError("Error creando materias: ${e.message}") }
            }
            .addOnFailureListener { e ->
                onError("Error creando usuario: ${e.message}")
            }
    }
}
