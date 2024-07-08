package com.example.roomlo.screens

enum class Screen(val route: String) {
    SplashScreen("splash_screen"),
    HomeView("home_screen"),
    ProfileScreen("profile_screen"),
    SignInScreen("signin_screen"),
    SignUpScreen("signup_screen"),
    MapView("map_screen"),
    PropertyView("property_screen"),
    WishlistView("wishlist_screen");

    companion object {
        var currentScreen: Screen = HomeView
    }
}
