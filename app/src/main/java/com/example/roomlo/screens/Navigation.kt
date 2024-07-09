package com.example.roomlo.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roomlo.viewmodels.AuthViewModel
import com.example.roomlo.viewmodels.DatabaseViewModel
import com.example.roomlo.viewmodels.RoomViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    roomViewModel: RoomViewModel,
    authViewModel: AuthViewModel,
    dbViewModel: DatabaseViewModel) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.HomeView.route) {
            HomeScreen(navController = navController, roomViewModel = roomViewModel, authViewModel = authViewModel, dbViewModel = dbViewModel)
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(dbViewModel = dbViewModel , navController = navController)
        }

        composable(Screen.MapView.route) {
            MapScreen(viewModel = roomViewModel, navController = navController)
        }


        composable(Screen.SignInScreen.route) {
            SignInScreen(navController = navController, authViewModel = authViewModel)
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController, authViewModel = authViewModel, dbViewModel = dbViewModel)
        }
    }
}
