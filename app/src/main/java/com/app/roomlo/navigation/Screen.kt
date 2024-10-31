package com.app.roomlo.navigation

enum class Screen(var route: String) {
    SplashScreen("splash_screen"),
    MainScaffoldView("home_screen"),
    ProfileScreen("profile_screen"),
    SignInScreen("signin_screen"),
    SignUpScreen("signup_screen"),
    MapView("map_screen"),
    PropertyView("property_screen"),
    WishlistView("wishlist_screen"),
    ListPropertyScreen("list_property_screen"),
    ListPropertyAddressView("Address"),
    ListPropertyDetailsView("Property Details"),
    ListPropertyImagesView("Property Images"),
    AskUserTypeScreen("ask_user_type_screen")
}
