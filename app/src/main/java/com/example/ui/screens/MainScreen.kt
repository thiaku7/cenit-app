package com.example.ui.screens

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.SubjectProgress
import com.example.data.StudyDay
import androidx.compose.foundation.BorderStroke
import com.example.ui.CenitViewModel
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: CenitViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf("inicio") }

    val subjects by viewModel.allSubjects.collectAsStateWithLifecycle()
    val studyDays by viewModel.allStudyDays.collectAsStateWithLifecycle()
    val selectedPhase by viewModel.selectedPhase.collectAsStateWithLifecycle()
    val readingMode by viewModel.readingMode.collectAsStateWithLifecycle()
    val studyStreak by viewModel.studyStreak.collectAsStateWithLifecycle()

    val primaryColor = if (readingMode) SepiaPrimary else Gold
    val secondaryColor = if (readingMode) SepiaSecondary else GoldDim
    val textLight = if (readingMode) SepiaText else WarmWhite
    val borderCol = if (readingMode) SepiaBorder else LineBorder
    val surfaceCol = if (readingMode) SepiaWhite else CharcoalMid
    val bgCol = if (readingMode) SepiaBackground else Black

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = bgCol,
        bottomBar = {
            NavigationBar(
                containerColor = if (readingMode) SepiaSurface else CharcoalMid,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("cenit_bottom_navigation")
            ) {
                val tabs = listOf(
                    Triple("inicio", "Inicio", Icons.Default.Home),
                    Triple("materias", "Materias", Icons.Default.List),
                    Triple("cron", "Enfoque", Icons.Default.PlayArrow),
                    Triple("leyes", "Método", Icons.Default.CheckCircle),
                    Triple("perfil", "Nosotros", Icons.Default.Person),
                    Triple("config", "Ajustes", Icons.Default.Settings)
                )

                tabs.forEach { (route, label, icon) ->
                    val isSelected = currentTab == route
                    val activeIconColor = if (readingMode) SepiaWhite else Black
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = route },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = activeIconColor,
                            selectedTextColor = primaryColor,
                            unselectedTextColor = textLight.copy(alpha = 0.5f),
                            unselectedIconColor = textLight.copy(alpha = 0.5f),
                            indicatorColor = primaryColor
                        ),
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                "inicio" -> DashboardTab(
                    viewModel = viewModel,
                    subjects = subjects,
                    studyDays = studyDays,
                    studyStreak = studyStreak,
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textLight,
                    borderColor = borderCol,
                    surfaceColor = surfaceCol
                )
                "materias" -> SubjectsTab(
                    viewModel = viewModel,
                    subjects = subjects,
                    selectedPhase = selectedPhase,
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textLight,
                    borderColor = borderCol,
                    surfaceColor = surfaceCol
                )
                "cron" -> PomodoroTab(
                    viewModel = viewModel,
                    subjects = subjects,
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textLight,
                    borderColor = borderCol,
                    surfaceColor = surfaceCol
                )
                "leyes" -> MethodTab(
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textLight,
                    borderColor = borderCol,
                    surfaceColor = surfaceCol
                )
                "perfil" -> AboutTab(
                    viewModel = viewModel,
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textLight,
                    borderColor = borderCol,
                    surfaceColor = surfaceCol
                )
                "config" -> ConfigTab(
                    viewModel = viewModel,
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textLight,
                    borderColor = borderCol,
                    surfaceColor = surfaceCol
                )
            }
        }
    }
}

// ==================== TABS IMPLEMENTATIONS ====================

@Composable
fun DashboardTab(
    viewModel: CenitViewModel,
    subjects: List<SubjectProgress>,
    studyDays: List<StudyDay>,
    studyStreak: Int,
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    val dailyQuote by viewModel.dailyQuote.collectAsStateWithLifecycle()

    val completedCount = subjects.count { it.status == "COMPLETED" }
    val progressPercent = if (subjects.isEmpty()) 0 else (completedCount * 100) / subjects.size
    val pendingSubjects = subjects.count { it.status != "COMPLETED" }
    val totalHoursStudied = (subjects.sumOf { it.timeSpentSeconds } / 3600f)

    // Estimate time remaining
    val estMonthsLeft = Math.ceil(((pendingSubjects.toDouble() / 42.0) * 36.0)).toInt()
    val timeRemainingStr = if (pendingSubjects == 0) {
        "CÉNIT COMPLETADO — MAESTRÍA ALCANZADA"
    } else {
        "$estMonthsLeft Meses Estimados"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Cénit
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PLAN MAESTRO CÉNIT",
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black),
                    color = primaryColor,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Consistencia · Poder · Sistemas",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    letterSpacing = 2.sp
                )
            }
        }

        // Daily Quote Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, primaryColor.copy(alpha = 0.6f), RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "CITA DIARIA DEL PLAN",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dailyQuote,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        ),
                        color = textColor
                    )
                }
            }
        }

        // Streak & Brief Stats
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Streak",
                            tint = if (studyStreak > 0) RedAccent else primaryColor.copy(alpha = 0.4f),
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$studyStreak Días",
                            style = MaterialTheme.typography.titleLarge,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Racha Activa",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.4f)
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Est Completion Time",
                            tint = primaryColor,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (pendingSubjects == 0) "Completado" else "$estMonthsLeft Meses",
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Tiempo Restante",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.4f)
                        )
                    }
                }
            }
        }

        // Global Progress Ticker
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PROGRESO GLOBAL DEL PLAN",
                            style = MaterialTheme.typography.labelSmall,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "$progressPercent%",
                            style = MaterialTheme.typography.titleLarge,
                            color = primaryColor,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { progressPercent.toFloat() / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = primaryColor,
                        trackColor = borderColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$completedCount de 42 Materias",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.6f)
                        )
                        Text(
                            text = String.format("%.1fh enfocadas", totalHoursStudied),
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }

        // Visual Calendar Grid
        item {
            Text(
                text = "CALENDARIO DE ESTUDIO (ÚLTIMOS 28 DÍAS)",
                style = MaterialTheme.typography.labelSmall,
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Visualización de consistencia en bloques enfocados:",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = textColor.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Generate last 28 days
                    val last28Days = remember(studyDays) {
                        val list = mutableListOf<Pair<String, Int>>() // DateString to Minutes
                        val cal = Calendar.getInstance()
                        cal.add(Calendar.DATE, -27) // Starts 28 days ago
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                        for (i in 0 until 28) {
                            val dateStr = sdf.format(cal.time)
                            val dayNum = cal.get(Calendar.DAY_OF_MONTH)
                            val entry = studyDays.firstOrNull { it.dateString == dateStr }
                            list.add(Pair("$dayNum", entry?.minutesStudied ?: 0))
                            cal.add(Calendar.DATE, 1)
                        }
                        list
                    }

                    // Render horizontal matrix layout representing GitHub commits
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Chunk into 4 rows of 7 columns
                        for (weekIndex in 0 until 4) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                for (dayOfWeek in 0 until 7) {
                                    val index = weekIndex * 7 + dayOfWeek
                                    if (index < last28Days.size) {
                                        val (label, mins) = last28Days[index]
                                        val boxColor = when {
                                            mins >= 45 -> primaryColor
                                            mins > 0 -> primaryColor.copy(alpha = 0.5f)
                                            else -> borderColor.copy(alpha = 0.3f)
                                        }
                                        val textCol = when {
                                            mins > 0 -> if (readingMode) SepiaWhite else Black
                                            else -> textColor.copy(alpha = 0.5f)
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .background(boxColor, RoundedCornerShape(2.dp))
                                                .border(1.dp, borderColor.copy(alpha = 0.2f), RoundedCornerShape(2.dp)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = label,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontSize = 9.sp,
                                                    fontWeight = if (mins > 0) FontWeight.Bold else FontWeight.Normal
                                                ),
                                                color = textCol
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Menos",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = textColor.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(10.dp).background(borderColor.copy(alpha = 0.3f)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(10.dp).background(primaryColor.copy(alpha = 0.5f)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(10.dp).background(primaryColor))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Más de 45m",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = textColor.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }

        // Timeline Block (static phase indicators)
        item {
            Text(
                text = "HOJA DE RUTA GENERAL",
                style = MaterialTheme.typography.labelSmall,
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )

            val timelineData = listOf(
                Triple("FASE I", "Fundamentos de la Mente", "Meses 1-4 | Autocontrol, estoicismo y hábitos."),
                Triple("FASE II", "Productividad y Sistemas", "Meses 5-9 | Ritmos, deep work y biohacking."),
                Triple("FASE III", "Tecnología y Herramientas", "Meses 10-16 | Python, IA, automatización y datos."),
                Triple("FASE IV", "Negocios, Finanzas e Influencia", "Meses 17-23 | Mercados, inversión, Lean Startup y ventas B2B."),
                Triple("FASE V", "Comunicación y Poder Social", "Meses 24-30 | Oratoria, persuasión, negociación y marca personal."),
                Triple("FASE VI", "Maestría Integrada y Legado", "Meses 31-36+ | Geopolítica, historia, ética del poder y escala.")
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor, RoundedCornerShape(4.dp))
                    .background(surfaceColor)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                timelineData.forEach { (phase, name, desc) ->
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = phase,
                                style = MaterialTheme.typography.labelSmall,
                                color = primaryColor,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = name,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = textColor
                            )
                        }
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = textColor.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(color = borderColor.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectsTab(
    viewModel: CenitViewModel,
    subjects: List<SubjectProgress>,
    selectedPhase: Int,
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    val phaseDescriptions = listOf(
        "Fase I · Meses 1–4\nFUNDAMENTOS DE LA MENTE\nSin una mente entrenada, ninguna herramienta tiene valor. Esta fase reprograma cómo resistes la adversidad.",
        "Fase II · Meses 5–9\nPRODUCTIVIDAD Y SISTEMAS PERSONALES\nDiseña sistemas que hagan el trabajo correcto de forma automática. Calendarios, PKM, hábitos e higiene circadiana.",
        "Fase III · Meses 10–16\nTECNOLOGÍA Y HERRAMIENTAS REALES\nProgramación en Python, automatización, IA aplicada, modelos, Looker Studio, ciberseguridad y SEO técnico.",
        "Fase IV · Meses 17–23\nNEGOCIOS, FINANZAS E INFLUENCIA\nIncentivos, optimización fiscal, diversificación patrimonial, Lean Startup, propuestas irresistibles y estrategia de foso empresarial.",
        "Fase V · Meses 24–30\nCOMUNICACIÓN Y PODER SOCIAL\nPirámide de Minto, persuasión de Cialdini, negociación FBI, liderazgo situacional y adquisición acelerada del inglés.",
        "Fase VI · Meses 31–36+\nMAESTRÍA INTEGRADA Y LEGADO\nGeopolítica realista, biografía corporativa aplicada, filosofía ética de poder, forecasting y síntesis operativa personal."
    )

    val currentPhaseDesc = phaseDescriptions.getOrElse(selectedPhase - 1) { "" }
    val phaseSubjects = subjects.filter { it.phase == selectedPhase }
    val phaseCompleted = phaseSubjects.count { it.status == "COMPLETED" }
    val phaseProgressPercent = if (phaseSubjects.isEmpty()) 0 else (phaseCompleted * 100) / phaseSubjects.size

    var expandedSubjectId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Phase selector horizontal chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(6) { index ->
                val phaseNum = index + 1
                val isSelected = phaseNum == selectedPhase
                val chipBg = if (isSelected) primaryColor else surfaceColor
                val chipTextCol = if (isSelected) {
                    if (readingMode) SepiaWhite else Black
                } else {
                    textColor.copy(alpha = 0.7f)
                }

                Box(
                    modifier = Modifier
                        .background(chipBg, RoundedCornerShape(4.dp))
                        .border(1.dp, if (isSelected) primaryColor else borderColor, RoundedCornerShape(4.dp))
                        .clickable { viewModel.setPhase(phaseNum) }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "FASE 0$phaseNum",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = chipTextCol
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Phase Summary Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = currentPhaseDesc,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 18.sp),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Progreso de Fase:",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor
                    )
                    Text(
                        text = "$phaseProgressPercent%",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = primaryColor
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { phaseProgressPercent.toFloat() / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = primaryColor,
                    trackColor = borderColor
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Subjects List
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(phaseSubjects, key = { it.id }) { subject ->
                val isExpanded = expandedSubjectId == subject.id
                SubjectCard(
                    subject = subject,
                    isExpanded = isExpanded,
                    onExpandClick = {
                        expandedSubjectId = if (isExpanded) null else subject.id
                    },
                    viewModel = viewModel,
                    readingMode = readingMode,
                    primaryColor = primaryColor,
                    textColor = textColor,
                    borderColor = borderColor,
                    surfaceColor = surfaceColor
                )
            }
        }
    }
}

@Composable
fun SubjectCard(
    subject: SubjectProgress,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    viewModel: CenitViewModel,
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    var notesText by remember(subject.notes) { mutableStateOf(subject.notes) }
    val ctx = LocalContext.current

    val badgeColor = when (subject.category) {
        "Mental" -> Color(0xFF7EB8D4)
        "Sistemas" -> Color(0xFF84D4A0)
        "Tech" -> Color(0xFFD49CEF)
        "Negocios" -> primaryColor
        "Comunicación" -> Color(0xFFEF9C9C)
        else -> Color(0xFFEFCF9C)
    }

    val statusColor = when (subject.status) {
        "COMPLETED" -> Color(0xFF2E7D32)
        "IN_PROGRESS" -> Color(0xFFFF9100)
        else -> textColor.copy(alpha = 0.4f)
    }

    val statusLabel = when (subject.status) {
        "COMPLETED" -> "COMPLETADA"
        "IN_PROGRESS" -> "EN PROGRESO"
        else -> "PENDIENTE"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isExpanded) primaryColor else borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onExpandClick() },
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = subject.id,
                            style = MaterialTheme.typography.labelSmall,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .border(1.dp, badgeColor, RoundedCornerShape(2.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = subject.category,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                color = badgeColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = subject.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Check indicator
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(statusColor, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subject.description,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, lineHeight = 16.sp),
                color = textColor.copy(alpha = 0.7f),
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = borderColor.copy(alpha = 0.5f))

                    Spacer(modifier = Modifier.height(12.dp))

                    // Topics list Title
                    Text(
                        text = "PUNTOS CLAVE DE ESTUDIO:",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Render topics
                    val topicsList = subject.topics.split(";")
                    topicsList.forEach { topic ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "→ ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = primaryColor
                            )
                            Text(
                                text = topic.trim(),
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                                color = textColor.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Duración: ${subject.durationText}",
                            style = MaterialTheme.typography.labelSmall,
                            color = primaryColor
                        )
                        val minsSpent = subject.timeSpentSeconds / 60
                        Text(
                            text = "Tiempo de Enfoque: ${minsSpent}m",
                            style = MaterialTheme.typography.labelSmall,
                            color = textColor.copy(alpha = 0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(color = borderColor.copy(alpha = 0.5f))

                    Spacer(modifier = Modifier.height(12.dp))

                    // Current Status Selector Buttons
                    Text(
                        text = "MODIFICAR ESTADO:",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "PENDING" to "PENDIENTE",
                            "IN_PROGRESS" to "EN PROCESO",
                            "COMPLETED" to "COMPLETADO"
                        ).forEach { (code, label) ->
                            val activeState = subject.status == code
                            val activeColor = when (code) {
                                "COMPLETED" -> Color(0xFF2E7D32)
                                "IN_PROGRESS" -> Color(0xFFFF9100)
                                else -> textColor.copy(alpha = 0.5f)
                            }
                            Button(
                                onClick = { viewModel.updateSubjectStatus(subject, code) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(36.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (activeState) activeColor else borderColor.copy(alpha = 0.3f),
                                    contentColor = if (activeState) {
                                        if (readingMode && code != "COMPLETED") SepiaText else SepiaWhite
                                    } else {
                                        textColor.copy(alpha = 0.6f)
                                    }
                                ),
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(2.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Personal notes
                    Text(
                        text = "APUNTES PERSONALES:",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = { Text("Escriba aquí insights, proyectos o conclusiones prácticas...", fontSize = 11.sp) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = borderColor,
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            cursorColor = primaryColor
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                        shape = RoundedCornerShape(4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                viewModel.selectSubjectForPomo(subject)
                                Toast.makeText(ctx, "Materia '${subject.name}' vinculada al Temporizador.", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor.copy(alpha = 0.1f),
                                contentColor = primaryColor
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Enfocar en Pomo", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp))
                        }

                        Button(
                            onClick = {
                                viewModel.updateSubjectNotes(subject, notesText)
                                Toast.makeText(ctx, "Apuntes guardados localmente.", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                contentColor = if (readingMode) SepiaWhite else Black
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("Guardar Apuntes", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PomodoroTab(
    viewModel: CenitViewModel,
    subjects: List<SubjectProgress>,
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    val pomoTimerActive by viewModel.pomoTimerActive.collectAsStateWithLifecycle()
    val pomoTimeRemaining by viewModel.pomoTimeRemaining.collectAsStateWithLifecycle()
    val pomoIsStudySession by viewModel.pomoIsStudySession.collectAsStateWithLifecycle()
    val pomoConfigStudyMinutes by viewModel.pomoConfigStudyMinutes.collectAsStateWithLifecycle()
    val pomoConfigRestMinutes by viewModel.pomoConfigRestMinutes.collectAsStateWithLifecycle()
    val selectedSubjectForPomo by viewModel.selectedSubjectForPomo.collectAsStateWithLifecycle()

    var showConfigDialog by remember { mutableStateOf(false) }
    var selectedSubjectDropdownOpen by remember { mutableStateOf(false) }

    val formattedTime = remember(pomoTimeRemaining) {
        val minutes = pomoTimeRemaining / 60
        val seconds = pomoTimeRemaining % 60
        String.format("%02d:%02d", minutes, seconds)
    }

    val progressFraction = remember(pomoTimeRemaining, pomoIsStudySession, pomoConfigStudyMinutes, pomoConfigRestMinutes) {
        val denom = if (pomoIsStudySession) pomoConfigStudyMinutes * 60f else pomoConfigRestMinutes * 60f
        if (denom > 0) pomoTimeRemaining / denom else 0f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "TEMPORIZADOR POMODORO",
            style = MaterialTheme.typography.labelLarge,
            color = primaryColor,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Main Timer Circle Visualization
        Box(
            modifier = Modifier
                .size(230.dp)
                .border(2.dp, if (pomoIsStudySession) primaryColor else Color(0xFFC0392B), CircleShape)
                .background(surfaceColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (pomoIsStudySession) "ESTUDIAR" else "DESCANSO",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (pomoIsStudySession) primaryColor else Color(0xFFC0392B),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 54.sp, fontWeight = FontWeight.Bold),
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (pomoIsStudySession) "${pomoConfigStudyMinutes}m" else "${pomoConfigRestMinutes}m",
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor.copy(alpha = 0.5f)
                )
            }
        }

        // Active Binding Tracker
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedSubjectDropdownOpen = true }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "VINCULAR MINUTOS DE ESTUDIO A:",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = selectedSubjectForPomo?.name ?: "Ninguna materia seleccionada (acumulación apagada)",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, fontWeight = FontWeight.Bold),
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = primaryColor)

                // Dropdown binder menu
                DropdownMenu(
                    expanded = selectedSubjectDropdownOpen,
                    onDismissRequest = { selectedSubjectDropdownOpen = false },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(surfaceColor)
                        .border(1.dp, borderColor)
                ) {
                    DropdownMenuItem(
                        text = { Text("Desvincular materia", style = MaterialTheme.typography.bodyMedium, color = textColor) },
                        onClick = {
                            viewModel.selectSubjectForPomo(null)
                            selectedSubjectDropdownOpen = false
                        }
                    )
                    subjects.forEach { subj ->
                        DropdownMenuItem(
                            text = { Text("[${subj.id}] ${subj.name}", style = MaterialTheme.typography.bodyMedium, color = textColor) },
                            onClick = {
                                viewModel.selectSubjectForPomo(subj)
                                selectedSubjectDropdownOpen = false
                            }
                        )
                    }
                }
            }
        }

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (pomoTimerActive) {
                        viewModel.pauseTimer()
                    } else {
                        viewModel.startTimer()
                    }
                },
                modifier = Modifier.weight(1f).height(46.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = if (readingMode) SepiaWhite else Black
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (pomoTimerActive) Icons.Default.Check else Icons.Default.PlayArrow,
                        contentDescription = "Trigger"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (pomoTimerActive) "PAUSAR" else "EMPEZAR",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = { viewModel.resetTimerToConfig() },
                modifier = Modifier.weight(1f).height(46.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = borderColor.copy(alpha = 0.3f),
                    contentColor = textColor
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "REINICIAR", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Open Config Button
        OutlinedButton(
            onClick = { showConfigDialog = true },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(42.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = primaryColor),
            border = BorderStroke(1.dp, primaryColor)
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Config")
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "AJUSTAR TIEMPOS", style = MaterialTheme.typography.labelSmall)
        }

        // Configuration dialog
        if (showConfigDialog) {
            var studyVal by remember { mutableStateOf(pomoConfigStudyMinutes.toFloat()) }
            var restVal by remember { mutableStateOf(pomoConfigRestMinutes.toFloat()) }

            AlertDialog(
                onDismissRequest = { showConfigDialog = false },
                title = {
                    Text(
                        text = "AJUSTAR TEMPORIZADOR",
                        style = MaterialTheme.typography.labelLarge,
                        color = primaryColor
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Enfoque/Estudio:", style = MaterialTheme.typography.bodyMedium, color = textColor)
                                Text("${studyVal.toInt()} min", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = primaryColor)
                            }
                            Slider(
                                value = studyVal,
                                onValueChange = { studyVal = it },
                                valueRange = 5f..60f,
                                colors = SliderDefaults.colors(thumbColor = primaryColor, activeTrackColor = primaryColor)
                            )
                        }

                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Descanso:", style = MaterialTheme.typography.bodyMedium, color = textColor)
                                Text("${restVal.toInt()} min", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = primaryColor)
                            }
                            Slider(
                                value = restVal,
                                onValueChange = { restVal = it },
                                valueRange = 1f..30f,
                                colors = SliderDefaults.colors(thumbColor = primaryColor, activeTrackColor = primaryColor)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.updatePomodoroConfigs(studyVal.toInt(), restVal.toInt())
                            showConfigDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor, contentColor = if (readingMode) SepiaWhite else Black)
                    ) {
                        Text("Aplicar", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfigDialog = false }) {
                        Text("Cancelar", color = textColor.copy(alpha = 0.6f))
                    }
                },
                containerColor = surfaceColor,
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}

@Composable
fun MethodTab(
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Core Methods
        Text(
            text = "CÓMO ESTUDIAR ESTO",
            style = MaterialTheme.typography.displayMedium,
            color = textColor,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        val methodology = listOf(
            Triple("📖", "Aprendizaje Activo", "Nunca leas pasivamente. Por cada 30 minutos de lectura o video, escribe 10 minutos de síntesis en tus propias palabras, sin mirar las notas."),
            Triple("🧪", "Práctica Real Inmediata", "Cada materia debe tener un proyecto real. No hay aprendizaje sin aplicación o acción. Si estudias ventas, vende algo. Si estudias código, programa algo."),
            Triple("🔁", "Revisión Espaciada", "Usa Anki o revisiones cada 1, 7, 14 y 30 días para consolidar lo crítico. Sin repetición activa, el 90% del conocimiento se disgrega en una semana."),
            Triple("📊", "Métricas Semanales", "Cada domingo audita: ¿cuántas horas estudié? ¿Qué aprendí exactamente? ¿Qué puse en práctica? Sin métricas honestas, no hay dirección estratégica."),
            Triple("🧠", "Enseñar para Aprender", "Explica cada tema complejo como si tuvieras que enseñárselo a un niño desde cero. Si no puedes explicarlo de forma simple, no lo dominas aún."),
            Triple("🔗", "Conexión Interdisciplinaria", "Constantemente busca cómo una disciplina interactúa con la otra. Los mejores creadores de valor detectan patrones cruzados entre campos lejanos.")
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.height(410.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(methodology) { (icon, title, desc) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                    colors = CardDefaults.cardColors(containerColor = surfaceColor),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(modifier = Modifier.padding(14.dp)) {
                        Text(text = icon, fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                        Column {
                            Text(text = title, style = MaterialTheme.typography.titleMedium, color = primaryColor, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = desc, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, lineHeight = 16.sp), color = textColor.copy(alpha = 0.8f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Code of Conduct
        Text(
            text = "CÓDIGO DE CONDUCTA",
            style = MaterialTheme.typography.displayMedium,
            color = if (readingMode) Color(0xFFC0392B) else RedAccent,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        val rules = listOf(
            "La consistencia vence al talento. Estudiar 3 horas todos los días es infinitamente más poderoso que estudiar 12 horas un fin de semana. La disciplina diaria esculpe la arquitectura cerebral.",
            "No avances si no entendiste. El conocimiento es una pirámide de dependencias. Nunca simules comprender — domina las matemáticas antes de la analítica avanzada.",
            "La aplicación real es obligatoria. Por cada materia que completes de las 42, debes desarrollar un microproyecto práctico. El saber sin hacer es solo entretenimiento.",
            "Protege el estudio de alta concentración. Apague notificaciones, desconecte redes. El trabajo cognitivo profundo es el único generador de un foso competitivo real.",
            "El ego es tu mayor limitante en el saber. Si asumes que ya sabes algo, cierras el portal de la curiosidad. Aborda cada nuevo tema con la mente del humilde principiante.",
            "Audita y ajusta la hoja de ruta. Este plan no es un texto sagrado petrificado — es una guía viva en constante perfeccionamiento cada tres meses.",
            "La higiene de tu hardware biológico es prioritaria. Sin sueño regulado circadiano, ejercicio de fuerza y nutrición cerebral adecuada, tu output intelectual cae al 50%.",
            "Filtra tu entorno radicalmente. Somos el promedio directo de las personas con las que reaccionamos y negociamos diariamente. Busca comunidades de extrema exigencia.",
            "Documentación obligatoria del trayecto. Mantén un registro escrito diario de lecciones aprendidas y errores superados. Duplica la velocidad de adquisición.",
            "El horizonte de tiempo lejano prevalece sobre la impaciencia. Habrá mesetas y frustraciones. La persistencia en las profundidades de la racha es el único camino real al Cénit."
        )

        rules.forEachIndexed { index, rule ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = String.format("%02d. ", index + 1),
                    style = MaterialTheme.typography.titleLarge,
                    color = if (readingMode) Color(0xFFC0392B) else RedAccent,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = rule,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 18.sp),
                    color = textColor.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun AboutTab(
    viewModel: CenitViewModel,
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "QUIÉNES SOMOS",
            style = MaterialTheme.typography.displayMedium,
            color = textColor,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "LOS FUNDADORES",
                    style = MaterialTheme.typography.labelSmall,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Thiago Ríos y Matías Maldonado somos dos estrategas y desarrolladores convencidos de que la educación formal rara vez equipa al individuo con las armas precisas del siglo XXI: fortaleza estoica, optimización automatizada de procesos y finanzas de océano azul.\n\n" +
                           "Confeccionamos CÉNIT como un manifiesto autocontenido de re-entrenamiento biológico y estratégico. Las 42 disciplinas forman un ecosistema cerrado que no repite redundancias y cataliza ideas uniendo lo teórico a micro-ejecuciones semanales.\n\n" +
                           "Deseamos que use esta bitácora no solo como un anotador, sino como escudo contra la inconsistencia.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.showWelcomeAgain() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor, contentColor = if (readingMode) SepiaWhite else Black),
            shape = RoundedCornerShape(4.dp)
        ) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Re-Ver Portal Inicial", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun ConfigTab(
    viewModel: CenitViewModel,
    readingMode: Boolean,
    primaryColor: Color,
    textColor: Color,
    borderColor: Color,
    surfaceColor: Color
) {
    val ctx = LocalContext.current
    val scrollState = rememberScrollState()

    val remindersEnabled by viewModel.remindersEnabled.collectAsStateWithLifecycle()
    val reminderHour by viewModel.reminderHour.collectAsStateWithLifecycle()
    val reminderMinute by viewModel.reminderMinute.collectAsStateWithLifecycle()
    val studyStreak by viewModel.studyStreak.collectAsStateWithLifecycle()

    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CONFIGURACIÓN",
            style = MaterialTheme.typography.displayMedium,
            color = textColor,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        // Reading Mode Toggle
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.toggleReadingMode() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MODO LECTURA",
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Cambiar los contrastes de la aplicación a una paleta sepia cálida ultra-agradable para lectura prolongada.",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = textColor.copy(alpha = 0.5f),
                        lineHeight = 16.sp
                    )
                }
                Switch(
                    checked = readingMode,
                    onCheckedChange = { viewModel.toggleReadingMode() },
                    colors = SwitchDefaults.colors(checkedThumbColor = primaryColor, checkedTrackColor = primaryColor.copy(alpha = 0.4f))
                )
            }
        }

        // Daily Reminders Toggle
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "RECORDATORIOS DIARIOS",
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Recibir avisos para mantener su consistencia diaria.",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                            color = textColor.copy(alpha = 0.5f),
                            lineHeight = 16.sp
                        )
                    }
                    Switch(
                        checked = remindersEnabled,
                        onCheckedChange = { viewModel.toggleReminders() },
                        colors = SwitchDefaults.colors(checkedThumbColor = primaryColor, checkedTrackColor = primaryColor.copy(alpha = 0.4f))
                    )
                }

                if (remindersEnabled) {
                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = borderColor.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "HORA DEL RECORDATORIO",
                                style = MaterialTheme.typography.labelSmall,
                                color = primaryColor,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = String.format("%02d:%02d h", reminderHour, reminderMinute),
                                style = MaterialTheme.typography.titleLarge,
                                color = textColor,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                TimePickerDialog(
                                    ctx,
                                    { _, hour, min -> viewModel.setReminderTime(hour, min) },
                                    reminderHour,
                                    reminderMinute,
                                    true
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor, contentColor = if (readingMode) SepiaWhite else Black),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("Cambiar Hora", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        // Active Streak Summary info
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (studyStreak > 0) Color(0xFFC0392B) else primaryColor,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "NOTIFICACIÓN DE RACHA ACTIVA",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (studyStreak > 0) "¡Su racha de $studyStreak días está activa y protegida en este dispositivo!" else "Racha inactiva. Complete una sesión Pomodoro hoy para encender su consistencia.",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp),
                        color = textColor.copy(alpha = 0.7f),
                        lineHeight = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Reset App Data
        Button(
            onClick = { showResetDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedAccent, contentColor = WarmWhite),
            shape = RoundedCornerShape(4.dp)
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Wipe")
            Spacer(modifier = Modifier.width(6.dp))
            Text("RESTABLECER TODO EL PROGRESO", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
        }

        // Logout Secure
        OutlinedButton(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
            border = BorderStroke(1.dp, borderColor),
            shape = RoundedCornerShape(4.dp)
        ) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Log out")
            Spacer(modifier = Modifier.width(6.dp))
            Text("CERRAR SESIÓN DE LICENCIA", style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Clear warning
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text("¿Desea restablecer todo?", style = MaterialTheme.typography.titleLarge, color = RedAccent) },
                text = { Text("Esta acción es irreversible. Se borrarán todas sus notas escritas, tiempos dedicados de Pomodoro, racha de días y calendarizaciones de CÉNIT.", style = MaterialTheme.typography.bodyMedium, color = textColor) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.resetAllAppProgress()
                            showResetDialog = false
                            Toast.makeText(ctx, "Progreso restablecido con éxito.", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RedAccent, contentColor = WarmWhite)
                    ) {
                        Text("Sí, restablecer", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text("Cancelar", color = textColor.copy(alpha = 0.6f))
                    }
                },
                containerColor = surfaceColor,
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}
