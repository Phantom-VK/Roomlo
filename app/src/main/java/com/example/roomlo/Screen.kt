package com.example.roomlo

sealed class Screen (val route : String){
    data object SplashScreen: Screen("splash_screen")
    data object HomeScreen: Screen("home_screen")
    data object ProfileScreen: Screen("profile_screen")
}