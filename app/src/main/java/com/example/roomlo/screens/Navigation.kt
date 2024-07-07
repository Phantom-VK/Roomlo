package com.example.roomlo.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roomlo.viewmodels.AuthViewModel
import com.example.roomlo.viewmodels.RoomViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: RoomViewModel,
    authViewModel: AuthViewModel
) {
     NavHost(navController = navController, startDestination = Screen.SignInScreen.route){
         composable(
            Screen.SplashScreen.route
         ){
                SplashScreen(navController = navController)
         }
         
         composable(
             Screen.HomeScreen.route
         ){
             HomeScreen(navController = navController, viewModel = viewModel, authViewModel = authViewModel)
         }

         composable(
             Screen.ProfileScreen.route
         ){
             ProfileScreen(viewModel = viewModel, navController = navController )
         }
         composable(
             Screen.SignInScreen.route
         ){
             SignInScreen(navController = navController, authViewModel = authViewModel)
         }
         composable(
             Screen.SignUpScreen.route
         ){
             SignUpScreen(navController = navController, authViewModel = authViewModel)
         }
     }

}