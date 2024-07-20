package com.app.roomlo.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.roomlo.data.PreferenceHelper

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Navigation(
    navController: NavHostController,
    preferenceHelper: PreferenceHelper,
) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController
            )
        }

        composable(Screen.HomeView.route) {
            HomeScreen(
                navController = navController,
                preferenceHelper = preferenceHelper
            )
        }

        composable(Screen.ProfileScreen.route) {
            ProfileScreen(
                navController = navController
            )
        }

        composable(Screen.MapView.route) {
            MapScreen( navController = navController)
        }


        composable(Screen.SignInScreen.route) {
            SignInScreen(
                navController = navController,
                preferenceHelper = preferenceHelper
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                navController = navController
            )
        }

        composable(Screen.ListPropertyScreen.route){
            ListPropertyScreen(navController = navController)

        }
    }
}
