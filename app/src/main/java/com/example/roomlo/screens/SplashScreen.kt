package com.example.roomlo.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.roomlo.R
import com.example.roomlo.data.PreferenceHelper
import com.example.roomlo.viewmodels.SharedViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    preferenceHelper: PreferenceHelper
) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )

        // Fetch user details
        val userId = preferenceHelper.userId
        if (userId != null) {
            sharedViewModel.fetchUserDetails()
        }

        // Delay to show splash screen and allow fetching data
        delay(1000L)

        // Navigate based on authentication state or fetched user details
        if (sharedViewModel.userDetails.value != null) {
            navController.navigate(Screen.HomeView.route)
        } else {
            navController.navigate(Screen.SignInScreen.route)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_screen_logo),
            contentDescription = "App Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}

