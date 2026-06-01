package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CenitViewModel
import com.example.ui.theme.*

@Composable
fun WelcomeScreen(
    viewModel: CenitViewModel,
    modifier: Modifier = Modifier
) {
    val readingMode by viewModel.readingMode.collectAsState()
    val scrollState = rememberScrollState()

    val bgBrush = if (readingMode) {
        Brush.verticalGradient(colors = listOf(SepiaBackground, SepiaSurface))
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF1E1402), // subtle radiant amber
                Color(0xFF080808)  // elegant dark
            )
        )
    }

    val primaryColor = if (readingMode) SepiaPrimary else Gold
    val textColor = if (readingMode) SepiaText else WarmWhite
    val cardColor = if (readingMode) SepiaWhite else CharcoalMid
    val borderColor = if (readingMode) SepiaBorder else LineBorder

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgBrush)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Crown Accent
        Text(
            text = "✦ C É N I T ✦",
            style = MaterialTheme.typography.labelLarge,
            color = primaryColor,
            letterSpacing = 6.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "BIENVENIDO AL\nPLAN MAESTRO",
            style = MaterialTheme.typography.displayMedium,
            color = textColor,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Introspective intro (inspired by HTML intro-block)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = primaryColor,
                    shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)
                )
                .background(primaryColor.copy(alpha = 0.05f))
                .padding(start = 20.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Este no es un plan de estudios ordinario. Es un sistema de construcción de poder personal, diseñado para transformar a alguien que parte desde cero en una persona que domina su mente, sus herramientas, su negocio y su entorno.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontStyle = FontStyle.Italic,
                    lineHeight = 24.sp,
                    fontSize = 15.sp
                ),
                color = textColor.copy(alpha = 0.9f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Founders Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "QUIÉNES SOMOS",
                    style = MaterialTheme.typography.labelLarge,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Fundado por Thiago Ríos y Matías Maldonado, CÉNIT nació con un objetivo inamovible: erradicar el estudio pasivo y la mediocridad intelectual.\n\n" +
                           "Desarrollamos este plan de 42 materias divididas en 6 fases estratégicas para guiar el cerebro humano a lo largo de un horizonte de 3 años, logrando un balance perfecto de autocontrol, eficiencia sistémica, automatización tecnológica, finanzas avanzadas, persuasión de masas y estrategia de legado.\n\n" +
                           "Cada materia exige aplicación práctica obligatoria. Hemos programado esta bitácora digital para acompañarle en cada racha, cada rincón de apuntes y cada sesión de enfoque profundo.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.8f),
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Instructions to begin
        Text(
            text = "Cada fase se construye sobre la anterior.\nCada materia es un eslabón único.",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                viewModel.dismissWelcomeScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("start_journey_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = if (readingMode) SepiaWhite else Color.Black
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "INICIAR MI CAMINO",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
