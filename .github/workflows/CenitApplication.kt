package com.example

import android.app.Application
import com.google.firebase.FirebaseApp

class CenitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
