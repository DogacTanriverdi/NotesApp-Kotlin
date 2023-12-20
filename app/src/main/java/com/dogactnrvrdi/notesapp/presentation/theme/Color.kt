package com.example.compose

import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF6750A4)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFE9DDFF)
val md_theme_light_onPrimaryContainer = Color(0xFF22005D)
val md_theme_light_secondary = Color(0xFF625B71)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFE8DEF8)
val md_theme_light_onSecondaryContainer = Color(0xFF1E192B)
val md_theme_light_tertiary = Color(0xFF7E5260)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFFFD9E3)
val md_theme_light_onTertiaryContainer = Color(0xFF31101D)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1C1B1E)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1C1B1E)
val md_theme_light_surfaceVariant = Color(0xFFE7E0EB)
val md_theme_light_onSurfaceVariant = Color(0xFF49454E)
val md_theme_light_outline = Color(0xFF7A757F)
val md_theme_light_inverseOnSurface = Color(0xFFF4EFF4)
val md_theme_light_inverseSurface = Color(0xFF313033)
val md_theme_light_inversePrimary = Color(0xFFCFBCFF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF6750A4)
val md_theme_light_outlineVariant = Color(0xFFCAC4CF)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFCFBCFF)
val md_theme_dark_onPrimary = Color(0xFF381E72)
val md_theme_dark_primaryContainer = Color(0xFF4F378A)
val md_theme_dark_onPrimaryContainer = Color(0xFFE9DDFF)
val md_theme_dark_secondary = Color(0xFFCBC2DB)
val md_theme_dark_onSecondary = Color(0xFF332D41)
val md_theme_dark_secondaryContainer = Color(0xFF4A4458)
val md_theme_dark_onSecondaryContainer = Color(0xFFE8DEF8)
val md_theme_dark_tertiary = Color(0xFFEFB8C8)
val md_theme_dark_onTertiary = Color(0xFF4A2532)
val md_theme_dark_tertiaryContainer = Color(0xFF633B48)
val md_theme_dark_onTertiaryContainer = Color(0xFFFFD9E3)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1C1B1E)
val md_theme_dark_onBackground = Color(0xFFE6E1E6)
val md_theme_dark_surface = Color(0xFF1C1B1E)
val md_theme_dark_onSurface = Color(0xFFE6E1E6)
val md_theme_dark_surfaceVariant = Color(0xFF49454E)
val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4CF)
val md_theme_dark_outline = Color(0xFF948F99)
val md_theme_dark_inverseOnSurface = Color(0xFF1C1B1E)
val md_theme_dark_inverseSurface = Color(0xFFE6E1E6)
val md_theme_dark_inversePrimary = Color(0xFF6750A4)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFCFBCFF)
val md_theme_dark_outlineVariant = Color(0xFF49454E)
val md_theme_dark_scrim = Color(0xFF000000)

/* *** Custom Colors *** */

// Light Colors
val custom_background_light = Color(0xFFFAF7FF)
val custom_primary_light = Color(0xFFEB9AFF)
val custom_text_color_light = Color(0xFF000000)
val custom_toolbar_color_light = Color(0xFFF3EAFF)
val custom_statusbar_color_light = Color(0xFFDDC5FF)
val custom_fab_color_light = Color(0xFFE9DCFE)
val custom_stroke_color_light = Color(0xFF80629C)
val custom_hint_color_light = Color(0xFF696969)

// Dark Colors
val custom_background_dark = Color(0xFF161616)
val custom_primary_dark = Color(0xFFCE8ADF)
val custom_text_color_dark = Color(0xFFFFFFFF)
val custom_toolbar_color_dark = Color(0xFF322736)
val custom_statusbar_color_dark = Color(0xFF1F1922)
val custom_fab_color_dark = Color(0xFF524059)
val custom_stroke_color_dark = Color(0xFFD2B6FF)
val custom_hint_color_dark = Color(0xFFCECECE)

sealed class ThemeColors(
    val customBackground: Color,
    val customPrimary: Color,
    val customTextColor: Color,
    val customToolbarColor: Color,
    val customStatusbarColor: Color,
    val customFabColor: Color,
    val customStrokeColor: Color,
    val customHintColor: Color
) {

    object Day: ThemeColors(
        customBackground = custom_background_light,
        customPrimary = custom_primary_light,
        customTextColor = custom_text_color_light,
        customToolbarColor = custom_toolbar_color_light,
        customStatusbarColor = custom_statusbar_color_light,
        customFabColor = custom_fab_color_light,
        customStrokeColor = custom_stroke_color_light,
        customHintColor = custom_hint_color_light
    )

    object Night: ThemeColors(
        customBackground = custom_background_dark,
        customPrimary = custom_primary_dark,
        customTextColor = custom_text_color_dark,
        customToolbarColor = custom_toolbar_color_dark,
        customStatusbarColor = custom_statusbar_color_dark,
        customFabColor = custom_fab_color_dark,
        customStrokeColor = custom_stroke_color_dark,
        customHintColor = custom_hint_color_dark
    )
}