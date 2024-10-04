package com.app.roomlo.navigation

import com.app.roomlo.screens.ListPropertyScaffoldScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.screens.HomeScreen
import com.app.roomlo.screens.MapScreen
import com.app.roomlo.screens.ProfileScreen
import com.app.roomlo.screens.SignInScreen
import com.app.roomlo.screens.SignUpScreen
import com.app.roomlo.screens.SplashScreen
import com.app.roomlo.viewmodels.AuthEvent
import com.app.roomlo.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Navigation(
    navController: NavHostController,
    preferenceHelper: PreferenceHelper,
) {
    val authViewModel:AuthViewModel= hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController,
                preferenceHelper = preferenceHelper
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
                authState = authViewModel.authState.collectAsState().value,
                onEvent={login: AuthEvent.Login ->  
                    authViewModel.onEvent(login)
                }
            )
        }

        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                navController = navController
            )
        }

        composable(Screen.ListPropertyScreen.route){
            ListPropertyScaffoldScreen(navController = navController, preferenceHelper)

        }




    }
}
