package com.app.roomlo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//LIGHT THEME
val LightThemeWhite = Color(0xFFFFFFFF)  // White
val LightThemeBlack = Color(0xFF000000)  // Black
val LightThemeRed = Color(0xFFE53935)  // Red
val LightThemeBlue = Color(0xFF2196F3)  // Light Blue
val LightThemeGreen = Color(0xFF66BB6A)  // Green
val SecondaryButtonTextLight = Color(0xFFFFFFFF)  // White
val LightThemeLightGrey = Color(0xFF999999)  // Light Gray
val LightThemeVLightGrey = Color(0xFFE2E2E2)  // Very Light Gray
val LightThemeDarkGrey = Color(0xFFBDBDBD)  // Dark Gray

//DARK THEME
val DarkThemeVeryDarkGrey = Color(0xFF212121)  // Very Dark Gray
val DarkThemeWhite = Color(0xFFFFFFFF)  // White
val DarkThemeRed = Color(0xFFEF5350)  // Red
val DarkThemeBlack = Color(0xFF000000)  // Black
val DarkThemeGreen = Color(0xFF66BB6A)  // Green
val DarkThemeBlue = Color(0xFF2196F3)  // Light Blue
val SecondaryButtonTextDark = Color(0xFF000000)  // Black
val DarkThemeDarkGrey = Color(0xFF292929)  // Dark Gray
val DarkThemeMedGrey = Color(0xFF616161)  // Medium Gray
val DarkThemeLightGrey = Color(0xFF616161)  // Light Gray

@Composable
fun getTextFieldColors(): TextFieldColors {
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.primary,
        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
        focusedTextColor = MaterialTheme.colorScheme.secondary,
        unfocusedTextColor = MaterialTheme.colorScheme.background,
        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        cursorColor = MaterialTheme.colorScheme.secondary
    )

    return colors
}
