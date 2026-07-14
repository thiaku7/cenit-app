package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.security.FirebaseSecurityService
import com.example.ui.CenitViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: CenitViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val securityService = remember { FirebaseSecurityService(context) }
    var enteredCode by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val verificationError by viewModel.verificationError.collectAsState()
    val readingMode by viewModel.readingMode.collectAsState()

    val bgBrush = if (readingMode) {
        Brush.verticalGradient(
            colors = listOf(SepiaBackground, SepiaSurface)
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF140D02),
                Color(0xFF080808),
                Color(0xFF080808)
            )
        )
    }

    val primaryColor = if (readingMode) SepiaPrimary else Gold
    val secondaryColor = if (readingMode) SepiaSecondary else GoldDim
    val textColor = if (readingMode) SepiaText else WarmWhite
    val borderColor = if (readingMode) SepiaBorder else LineBorder

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgBrush)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        if (!readingMode) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "DOCUMENTO CLASIFICADO · ÉLITE",
                style = MaterialTheme.typography.labelSmall,
                color = primaryColor,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "CÉNIT",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = if (readingMode) SepiaAccentGold else Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 8.sp
            )

            Text(
                text = "SISTEMA DE SEGURIDAD",
                style = MaterialTheme.typography.labelLarge,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = if (readingMode) SepiaWhite else CharcoalMid
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "INGRESE SU CÓDIGO ÚNICO",
                        style = MaterialTheme.typography.labelLarge,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "La licencia de acceso vincula este dispositivo permanentemente a su racha de estudio.",
                        style = MaterialTheme.typography.labelSmall,
                        color = textColor.copy(alpha = 0.6f),
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = enteredCode,
                        onValueChange = {
                            enteredCode = it
                            if (verificationError != null) {
                                viewModel.clearVerificationError()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("access_code_input"),
                        label = { Text("Código de Acceso") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Security code",
                                tint = primaryColor
                            )
                        },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = borderColor,
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedLabelColor = primaryColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.5f),
                            cursorColor = primaryColor
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    verificationError?.let { error ->
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = RedAccent,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.clearVerificationError()
                            securityService.verifyAccessCode(enteredCode, object : FirebaseSecurityService.SecurityCallback {
                                override fun onSuccess() {
                                    isLoading = false
                                    viewModel.setFirestoreUserId(securityService.deviceId)
                                    viewModel.verifyCodeDirectly(enteredCode)
                                }

                                override fun onError(message: String) {
                                    isLoading = false
                                    viewModel.setVerificationError(message)
                                }
                            })
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = if (readingMode) SepiaWhite else Black
                        ),
                        shape = RoundedCornerShape(4.dp),
                        enabled = !isLoading && enteredCode.trim().isNotEmpty()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = if (readingMode) SepiaWhite else Black,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "VERIFICAR E INGRESAR",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
