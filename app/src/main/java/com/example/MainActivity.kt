package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CenitViewModel
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            val viewModel: CenitViewModel = viewModel()
            val securityVerified by viewModel.securityVerified.collectAsState()
            val showWelcomeScreen by viewModel.showWelcomeScreen.collectAsState()
            val readingMode by viewModel.readingMode.collectAsState()

            MyApplicationTheme(readingMode = readingMode) {
                Crossfade(
                    targetState = securityVerified,
                    label = "portal_transition",
                    modifier = Modifier.fillMaxSize()
                ) { verified ->
                    if (!verified) {
                        LoginScreen(viewModel = viewModel)
                    } else {
                        Crossfade(
                            targetState = showWelcomeScreen,
                            label = "welcome_transition"
                        ) { welcome ->
                            if (welcome) {
                                WelcomeScreen(viewModel = viewModel)
                            } else {
                                MainScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
