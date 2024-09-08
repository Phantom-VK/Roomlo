package com.app.roomlo.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.screens.HomeScreen
import com.app.roomlo.screens.ListPropertyScreen
import com.app.roomlo.screens.MapScreen
import com.app.roomlo.screens.ProfileScreen
import com.app.roomlo.screens.SignInScreen
import com.app.roomlo.screens.SignUpScreen
import com.app.roomlo.screens.SplashScreen

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
                navController = navController,
                preferenceHelper = preferenceHelper
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

        //old screen for listing property
        composable(Screen.ListPropertyScreen.route){
            ListPropertyScreen(navController = navController, preferenceHelper)

        }



    }
}
