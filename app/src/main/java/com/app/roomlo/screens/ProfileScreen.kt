package com.app.roomlo.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.roomlo.data.User
import com.app.roomlo.screens.components.ProfileImage
import com.app.roomlo.screens.components.UnderlineTextField
import com.app.roomlo.ui.theme.baloo
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.DatabaseViewModel
import com.app.roomlo.viewmodels.SharedViewModel
import com.app.roomlo.viewmodels.UserProfileViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(

    navController: NavController,
) {
    val context = LocalContext.current
    val profileViewModel: UserProfileViewModel = hiltViewModel<UserProfileViewModel>()
    val sharedViewModel: SharedViewModel = hiltViewModel<SharedViewModel>()
    val dbViewModel: DatabaseViewModel = hiltViewModel<DatabaseViewModel>()


    val user by sharedViewModel.userDetails.collectAsState()


    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobilenumber by remember { mutableStateOf("") }
    var wpnumber by remember { mutableStateOf("") }
    var profilePictureUrl by remember { mutableStateOf("") }


    // Update local state values when user changes
    LaunchedEffect(user) {

        user?.let { user1 ->
            name = user1.name
            address = user1.address
            email = user1.email
            mobilenumber = user1.mobilenumber
            wpnumber = user1.wpnumber
            profilePictureUrl = user1.profileImageUrl
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.small1,
                end = MaterialTheme.dimens.small1
            )
        ) {
            ProfileImage(profileViewModel, profilePictureUrl, navController)
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = name,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                color = MaterialTheme.colorScheme.background
            )

            UnderlineTextField(
                value = name,
                onValueChange = { name = it },
                hint = "Name",
                keyboardType = KeyboardType.Text,
                imageVector = Icons.Filled.AccountCircle
            )

            UnderlineTextField(
                value = address,
                onValueChange = { address = it },
                hint = "Address",
                keyboardType = KeyboardType.Text,
                imageVector = Icons.Filled.LocationOn
            )

            UnderlineTextField(
                value = email,
                onValueChange = { email = it },
                hint = "Email",
                keyboardType = KeyboardType.Email,
                imageVector = Icons.Outlined.Email
            )

            UnderlineTextField(
                value = mobilenumber,
                onValueChange = { mobilenumber = it },
                hint = "Mobile Number",
                keyboardType = KeyboardType.Number,
                imageVector = Icons.Filled.Call
            )

            UnderlineTextField(
                value = wpnumber,
                onValueChange = { wpnumber = it },
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

                    dbViewModel.updateUserDetails(
                        User(
                            name = name.trim(),
                            address = address.trim(),
                            email = email.trim(),
                            wpnumber = wpnumber.trim(),
                            mobilenumber = mobilenumber.trim()
                        ), context
                    )
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.padding(top = MaterialTheme.dimens.small1)
            ) {
                Text(text = "Save", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }

}

