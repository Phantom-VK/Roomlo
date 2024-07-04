package com.example.roomlo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryTextDark,
    secondary = SecondaryButtonDark,
    tertiary = PrimaryButtonDark,
    background = BackgroundDark,
    surface = BackgroundDark,
    onPrimary = PrimaryButtonTextDark,
    onSecondary = SecondaryButtonTextLight,
    onTertiary = BorderDark,
    onBackground = PrimaryTextDark,
    onSurface = PrimaryTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTextLight,
    secondary = SecondaryButtonLight,
    tertiary = PrimaryButtonLight,
    background = BackgroundLight,
    surface = BackgroundLight,
    onPrimary = PrimaryButtonTextLight,
    onSecondary = SecondaryButtonTextDark,
    onTertiary = BorderLight,
    onBackground = PrimaryTextLight,
    onSurface = PrimaryTextLight
)


@Composable
fun RoomLoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
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
        typography = Typography,
        content = content,
    )
}