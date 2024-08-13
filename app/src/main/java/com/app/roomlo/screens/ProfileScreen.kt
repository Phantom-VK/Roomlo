package com.app.roomlo.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.roomlo.dataclasses.User
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.screens.components.ProfileImage
import com.app.roomlo.screens.components.UnderlineTextField
import com.app.roomlo.ui.theme.baloo
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.UserViewModel
import com.app.roomlo.viewmodels.SharedViewModel
import com.app.roomlo.viewmodels.UserProfileViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper
) {
    val context = LocalContext.current
    val profileViewModel: UserProfileViewModel = hiltViewModel()
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val dbViewModel: UserViewModel = hiltViewModel()

    val user by sharedViewModel.userDetails.collectAsState()

    var userDetails by remember { mutableStateOf(User()) }

    // Update local state values when user changes
    LaunchedEffect(user) {
        user?.let {
            userDetails = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(navController)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.small1,
                end = MaterialTheme.dimens.small1
            )
        ) {
            ProfileImage(profileViewModel, userDetails.profileImageUrl, navController)
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = userDetails.name,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                color = MaterialTheme.colorScheme.background
            )

            ProfileTextField(
                value = userDetails.name,
                onValueChange = { userDetails = userDetails.copy(name = it) },
                hint = "Name",
                keyboardType = KeyboardType.Text,
                imageVector = Icons.Filled.AccountCircle
            )

            ProfileTextField(
                value = userDetails.address,
                onValueChange = { userDetails = userDetails.copy(address = it) },
                hint = "Address",
                keyboardType = KeyboardType.Text,
                imageVector = Icons.Filled.LocationOn
            )

            ProfileTextField(
                value = userDetails.email,
                onValueChange = { userDetails = userDetails.copy(email = it) },
                hint = "Email",
                keyboardType = KeyboardType.Email,
                imageVector = Icons.Outlined.Email
            )

            ProfileTextField(
                value = userDetails.mobilenumber,
                onValueChange = { userDetails = userDetails.copy(mobilenumber = it) },
                hint = "Mobile Number",
                keyboardType = KeyboardType.Number,
                imageVector = Icons.Filled.Call
            )

            ProfileTextField(
                value = userDetails.wpnumber,
                onValueChange = { userDetails = userDetails.copy(wpnumber = it) },
                hint = "WhatsApp Number",
                keyboardType = KeyboardType.Number,
                imageVector = Icons.Filled.Call
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* TODO: Implement change password functionality */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small2),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Change Password", color = MaterialTheme.colorScheme.secondary)
            }

            Button(
                onClick = {
                    preferenceHelper.username = userDetails.name
                    dbViewModel.updateUserDetails(userDetails, context)
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.padding(top = MaterialTheme.dimens.small1)
            ) {
                Text(text = "Save", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun ProfileHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimens.medium2),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Text(
            text = "Profile",
            fontFamily = baloo,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = MaterialTheme.dimens.small2)
        )
    }
}

@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardType: KeyboardType,
    imageVector: ImageVector
) {
    UnderlineTextField(
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        keyboardType = keyboardType,
        imageVector = imageVector
    )
}
