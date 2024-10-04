package com.app.roomlo.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.roomlo.R
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.navigation.Screen
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.AuthEvent
import com.app.roomlo.viewmodels.AuthState
import com.app.roomlo.viewmodels.AuthViewModel
import com.app.roomlo.viewmodels.SharedViewModel


@Composable
fun SignInScreen(
	navController: NavHostController
) {
	val authViewModel: AuthViewModel= hiltViewModel()
	val authState by authViewModel.authState.collectAsState()
	var email by remember {
		mutableStateOf("")
	}
	var password by remember {
		mutableStateOf("")
	}
	val passwordVisible = remember {
		mutableStateOf(false)
	}
	val context = LocalContext.current

	//TODO White screen after splashscreen page bug
	LaunchedEffect(authState) {
		when (authState) {
			is AuthState.Authenticated -> {
				navController.navigate(Screen.HomeView.route)
			}

			is AuthState.Error -> Toast.makeText(
				context,
				(authState as AuthState.Error).message, Toast.LENGTH_SHORT
			).show()

			else -> Unit
		}
	}
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface) //TODO Color is not showing black
			.padding(MaterialTheme.dimens.medium2),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Start,
			verticalAlignment = Alignment.CenterVertically
		) {
			/* Image(
				 painter = painterResource(id = R.drawable.keyhole_light),
				 contentDescription = "KeyholeIcon",
				 contentScale = ContentScale.Fit,
				 modifier = Modifier.size(MaterialTheme.dimens.logoSize + 30.dp)

			 )*/
			Text(
				text = "Sign in/Login",
				fontSize = MaterialTheme.typography.titleLarge.fontSize,
				modifier = Modifier.padding(top = 100.dp)
			)

		}
		Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
		// User ID TextField
		OutlinedTextField(
			value = email,
			onValueChange = { email = it },
			label = {
				Text(
					"Mobile number /email id",
					style = MaterialTheme.typography.bodyMedium
				)
			},
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Email
			),
			singleLine = true,
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = MaterialTheme.dimens.small1),
			colors = TextFieldDefaults.colors(
				focusedContainerColor = MaterialTheme.colorScheme.surface,
				focusedLabelColor = MaterialTheme.colorScheme.secondary,
				focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
				focusedTextColor = MaterialTheme.colorScheme.secondary,
				unfocusedContainerColor = MaterialTheme.colorScheme.surface
			)
		)

		OutlinedTextField(
			value = password,
			onValueChange = { password = it },
			label = { Text("Password", style = MaterialTheme.typography.bodyMedium) },
			singleLine = true,
			trailingIcon = {
				IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
					Icon(
						imageVector = if (passwordVisible.value)
							ImageVector.vectorResource(R.drawable.baseline_visibility_24)
						else ImageVector.vectorResource(R.drawable.baseline_visibility_off_24),
						contentDescription = "Password visibility",
						tint = if (passwordVisible.value) colorResource(id = R.color.purple_700) else Color.Gray
					)
				}
			},
			visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Password
			),

			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = MaterialTheme.dimens.medium2),
			colors = TextFieldDefaults.colors(
				focusedContainerColor = MaterialTheme.colorScheme.surface,
				focusedLabelColor = MaterialTheme.colorScheme.secondary,
				focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
				focusedTextColor = MaterialTheme.colorScheme.secondary,
				unfocusedContainerColor = MaterialTheme.colorScheme.surface,
				errorLabelColor = MaterialTheme.colorScheme.error,

				)
		)

		// Sign In Button
		/*Button(
			onClick = {
				onEvent(AuthEvent.Login(email,password))

			},
			enabled = authState != AuthState.Loading,
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = MaterialTheme.dimens.small3)
				.height(MaterialTheme.dimens.logoSize),
			colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
		) {
			Text(text = "Sign In", color = MaterialTheme.colorScheme.primary)
		}*/
		Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
			.fillMaxWidth()
			.padding(start = 3.dp)) {
			Text(
				text = "Forgot password?",
				style= TextStyle(textDecoration = TextDecoration.Underline),
				color = Color(0XFF0066FF),
				modifier = Modifier.clickable { /*TODO*/ })
		}

		// Sign Up Text
		/* TextButton(onClick = {
			 navController.navigate(Screen.SignUpScreen.route)
		 }) {
			 Text(
				 text = "Don't have an account? Sign Up.",
				 color = MaterialTheme.colorScheme.secondary,
				 textAlign = TextAlign.Center,
				 fontSize = MaterialTheme.typography.titleMedium.fontSize
			 )
		 }*/
		Box(
			modifier = Modifier
				.height(22.dp)
				.width(65.dp)
				.clip(RoundedCornerShape(7.dp))
				.background(
					brush = Brush.verticalGradient(
						colors = listOf(Color(0xFFD9E0F6), Color(0xFF006CCF)),

						)
				)
				.clickable { authViewModel.login(email = email,password=password) }
			, contentAlignment = Alignment.Center
		) {
			Text("Continue", color = Color.White)
		}

	}
}




