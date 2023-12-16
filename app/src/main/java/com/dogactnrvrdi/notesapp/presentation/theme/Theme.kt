package com.example.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    background = ThemeColors.Day.customBackground,
    primary = ThemeColors.Day.customPrimary,
    surface = ThemeColors.Day.customTextColor,
    secondary = ThemeColors.Day.customToolbarColor,
    secondaryContainer = ThemeColors.Day.customStatusbarColor,
    primaryContainer = ThemeColors.Day.customFabColor,
    onPrimary = ThemeColors.Day.customStrokeColor,
    onError = ThemeColors.Day.customHintColor
)


private val DarkColors = darkColorScheme(
    background = ThemeColors.Night.customBackground,
    primary = ThemeColors.Night.customPrimary,
    surface = ThemeColors.Night.customTextColor,
    secondary = ThemeColors.Night.customToolbarColor,
    secondaryContainer = ThemeColors.Night.customStatusbarColor,
    primaryContainer = ThemeColors.Night.customFabColor,
    onPrimary = ThemeColors.Night.customStrokeColor,
    onError = ThemeColors.Night.customHintColor
)

@Composable
fun NotesAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}