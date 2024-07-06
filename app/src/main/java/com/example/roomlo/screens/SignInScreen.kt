package com.example.roomlo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.roomlo.R
import com.example.roomlo.screens.components.CustomTextField
import com.example.roomlo.ui.theme.dimens
import com.example.roomlo.viewmodels.RoomViewModel


@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: RoomViewModel = RoomViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) //TODO Color is not showing black
            .padding(MaterialTheme.dimens.medium2),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.keyhole_light),
                contentDescription = "KeyholeIcon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(MaterialTheme.dimens.logoSize+30.dp)
                // TODO Add size

            )

        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.large))
        // User ID TextField
        CustomTextField(
            inputValue = viewModel.mobilenumber,
            label = "User Id",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ))

        CustomTextField(
            inputValue = viewModel.password,
            label = "Password",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ))

        // Sign In Button
        Button(
            onClick = {
                navController.navigate(Screen.HomeScreen.route)

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.small3)
                .height(MaterialTheme.dimens.logoSize),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text(text = "Sign In", color = MaterialTheme.colorScheme.primary)
        }

        // Sign Up Text
        TextButton(onClick = {
            navController.navigate(Screen.SignUpScreen.route)
        }) {
            Text(
                text = "Don't have an account? Sign Up.",
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInPage() {
    SignInScreen(navController = NavHostController(LocalContext.current))
}

