package com.example.roomlo.ui.theme

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RoomLoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+,
    activity: Activity = LocalContext.current as Activity,
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

    val window = calculateWindowSizeClass(activity = activity)
    val config = LocalConfiguration.current
    var typography = CompactTypography
    var appDimens = CompactDimens

    when(window.widthSizeClass){
        WindowWidthSizeClass.Compact -> {
            if(config.screenWidthDp <= 360){
                appDimens = CompactSmallDimens
                typography = CompactSmallTypography
            }else if (config.screenWidthDp < 599){
                appDimens = CompactMediumDimens
                typography = CompactMediumTypography
            }else{
                appDimens = CompactDimens
                typography = CompactTypography
            }
        }

        WindowWidthSizeClass.Medium -> {
            appDimens = MediumDimens
            typography = CompactTypography
        }

        WindowWidthSizeClass.Expanded -> {
            appDimens = ExpandedDimens
            typography = ExpandedTypography
        }

        else -> {
            appDimens = ExpandedDimens
            typography = ExpandedTypography
        }
    }

    ProvideAppUtils(appDimens = appDimens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}

val MaterialTheme.dimens
    @Composable
    get() = LocalAppDimens.current