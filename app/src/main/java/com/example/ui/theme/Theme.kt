package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CenitDarkColorScheme = darkColorScheme(
    primary = Gold,
    secondary = GoldDim,
    tertiary = WarmWhite,
    background = Black,
    surface = CharcoalMid,
    onPrimary = Black,
    onSecondary = WarmWhite,
    onTertiary = Black,
    onBackground = WarmWhite,
    onSurface = WarmWhite,
    outline = LineBorder
)

private val CenitSepiaColorScheme = lightColorScheme(
    primary = SepiaPrimary,
    secondary = SepiaSecondary,
    tertiary = SepiaAccentGold,
    background = SepiaBackground,
    surface = SepiaSurface,
    onPrimary = SepiaWhite,
    onSecondary = SepiaText,
    onTertiary = SepiaWhite,
    onBackground = SepiaText,
    onSurface = SepiaText,
    outline = SepiaBorder
)

@Composable
fun MyApplicationTheme(
    readingMode: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (readingMode) CenitSepiaColorScheme else CenitDarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
