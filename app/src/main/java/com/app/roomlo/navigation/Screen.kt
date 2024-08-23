package com.app.roomlo.navigation

enum class Screen(var route: String) {
    SplashScreen("splash_screen"),
    HomeView("home_screen"),
    ProfileScreen("profile_screen"),
    SignInScreen("signin_screen"),
    SignUpScreen("signup_screen"),
    MapView("map_screen"),
    PropertyView("property_screen"),
    WishlistView("wishlist_screen"),
    ListPropertyScreen("list_property_screen"),
    ListPropertyAddressView("Address"),
    ListPropertyScaffoldScreen("list_property_scaffold_screen"),
    ListPropertyDetailsView("Property Details"),
    ListPropertyImagesView("Property Images"),
    ListPropertyCurrentScreen(ListPropertyAddressView.route)
}
