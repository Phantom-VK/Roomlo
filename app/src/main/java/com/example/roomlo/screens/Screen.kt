package com.example.roomlo.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen( val route: String)  {
    data object SplashScreen : Screen("splash_screen")
    data object HomeScreen : Screen("home_screen")
    data object ProfileScreen : Screen( "profile_screen")
    data object SignInScreen : Screen( "signin_screen")

    data object SignUpScreen : Screen("signup_screen")


    //Code for home screen drawer screen
}