package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = pink_theme_light_primary,
    onPrimary = pink_theme_light_onPrimary,
    primaryContainer = pink_theme_light_primaryContainer,
    onPrimaryContainer = pink_theme_light_onPrimaryContainer,
    secondary = pink_theme_light_secondary,
    onSecondary = pink_theme_light_onSecondary,
    secondaryContainer = pink_theme_light_secondaryContainer,
    onSecondaryContainer = pink_theme_light_onSecondaryContainer,
    tertiary = pink_theme_light_tertiary,
    onTertiary = pink_theme_light_onTertiary,
    tertiaryContainer = pink_theme_light_tertiaryContainer,
    onTertiaryContainer = pink_theme_light_onTertiaryContainer,
    error = pink_theme_light_error,
    errorContainer = pink_theme_light_errorContainer,
    onError = pink_theme_light_onError,
    onErrorContainer = pink_theme_light_onErrorContainer,
    background = pink_theme_light_background,
    onBackground = pink_theme_light_onBackground,
    surface = pink_theme_light_surface,
    onSurface = pink_theme_light_onSurface,
    surfaceVariant = pink_theme_light_surfaceVariant,
    onSurfaceVariant = pink_theme_light_onSurfaceVariant,
    outline = pink_theme_light_outline,
    inverseOnSurface = pink_theme_light_inverseOnSurface,
    inverseSurface = pink_theme_light_inverseSurface,
    inversePrimary = pink_theme_light_inversePrimary,
    surfaceTint = pink_theme_light_surfaceTint,
    outlineVariant = pink_theme_light_outlineVariant,
    scrim = pink_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = pink_theme_dark_primary,
    onPrimary = pink_theme_dark_onPrimary,
    primaryContainer = pink_theme_dark_primaryContainer,
    onPrimaryContainer = pink_theme_dark_onPrimaryContainer,
    secondary = pink_theme_dark_secondary,
    onSecondary = pink_theme_dark_onSecondary,
    secondaryContainer = pink_theme_dark_secondaryContainer,
    onSecondaryContainer = pink_theme_dark_onSecondaryContainer,
    tertiary = pink_theme_dark_tertiary,
    onTertiary = pink_theme_dark_onTertiary,
    tertiaryContainer = pink_theme_dark_tertiaryContainer,
    onTertiaryContainer = pink_theme_dark_onTertiaryContainer,
    error = pink_theme_dark_error,
    errorContainer = pink_theme_dark_errorContainer,
    onError = pink_theme_dark_onError,
    onErrorContainer = pink_theme_dark_onErrorContainer,
    background = pink_theme_dark_background,
    onBackground = pink_theme_dark_onBackground,
    surface = pink_theme_dark_surface,
    onSurface = pink_theme_dark_onSurface,
    surfaceVariant = pink_theme_dark_surfaceVariant,
    onSurfaceVariant = pink_theme_dark_onSurfaceVariant,
    outline = pink_theme_dark_outline,
    inverseOnSurface = pink_theme_dark_inverseOnSurface,
    inverseSurface = pink_theme_dark_inverseSurface,
    inversePrimary = pink_theme_dark_inversePrimary,
    surfaceTint = pink_theme_dark_surfaceTint,
    outlineVariant = pink_theme_dark_outlineVariant,
    scrim = pink_theme_dark_scrim,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,//isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypography,
        content = content
    )
}