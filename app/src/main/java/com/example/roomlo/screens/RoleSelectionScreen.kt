package com.example.roomlo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomlo.data.PreferenceHelper

@Composable
fun RoleSelectionScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Please select your role")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                preferenceHelper.isOwner = false
                navController.navigate(Screen.SignUpScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("I am a User", color = MaterialTheme.colorScheme.surface)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                preferenceHelper.isOwner = true
                navController.navigate(Screen.SignUpScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("I am a Seller", color = MaterialTheme.colorScheme.surface)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString("Already have an account? Sign in"),
            onClick = {
                navController.navigate(Screen.SignInScreen.route)
            },
            style = TextStyle(color = MaterialTheme.colorScheme.secondary)
        )
    }
}
