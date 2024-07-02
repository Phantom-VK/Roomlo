package com.example.roomlo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    viewModel: RoomViewModel
) {
     NavHost(navController = navController, startDestination = Screen.SplashScreen.route){
         composable(
            Screen.SplashScreen.route
         ){
                SplashScreen(navController = navController)
         }
         
         composable(
             Screen.HomeScreen.route
         ){
             HomeScreen(navController = navController, RoomViewModel())
         }

         composable(
             Screen.ProfileScreen.route
         ){
             ProfileScreen(viewModel = viewModel, navController = navController )
         }
     }

}