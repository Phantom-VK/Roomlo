package com.app.roomlo.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
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
import com.app.roomlo.viewmodels.SharedViewModel
import com.app.roomlo.viewmodels.UserProfileViewModel
import com.app.roomlo.viewmodels.UserViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ProfileScreen(
    navController: NavController,
    preferenceHelper: PreferenceHelper,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val profileViewModel: UserProfileViewModel = hiltViewModel()
    val dbViewModel: UserViewModel = hiltViewModel()

    //TODO make profile fields editable

    var name by remember { mutableStateOf(preferenceHelper.username) }
    var email by remember { mutableStateOf(preferenceHelper.useremail) }
    var mobileNumber by remember { mutableStateOf(preferenceHelper.mobilenumber) }
    var address by remember { mutableStateOf(preferenceHelper.userAddress) }
    var wpNumber by remember { mutableStateOf(preferenceHelper.wpnumber) }

    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        sharedViewModel.fetchUserDetails()
    }

    sharedViewModel.userDetails.collectAsState().value?.let { user ->
        name = user.name
        email = user.email
        mobileNumber = user.mobilenumber
        address = user.address
        wpNumber = user.wpnumber
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
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1)
        ) {
            ProfileImage(profileViewModel, preferenceHelper.profileImageUrl, navController)
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = name,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                color = MaterialTheme.colorScheme.background
            )

            EditableProfileTextField(
                value = name,
                onValueChange = { name = it },
                hint = "Name",
                keyboardType = KeyboardType.Text,
                imageVector = Icons.Filled.AccountCircle
            )

            EditableProfileTextField(
                value = address,
                onValueChange = { address = it },
                hint = "Address",
                keyboardType = KeyboardType.Text,
                imageVector = Icons.Filled.LocationOn
            )

            EditableProfileTextField(
                value = email,
                onValueChange = { email = it },
                hint = "Email",
                keyboardType = KeyboardType.Email,
                imageVector = Icons.Outlined.Email
            )

            EditableProfileTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                hint = "Mobile Number",
                keyboardType = KeyboardType.Number,
                imageVector = Icons.Filled.Call
            )

            EditableProfileTextField(
                value = wpNumber,
                onValueChange = { wpNumber = it },
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
                    if (isEditing) {
                        // Save changes
                        preferenceHelper.apply {
                            username = name
                            useremail = email
                            mobilenumber = mobileNumber
                            userAddress = address
                            wpnumber = wpNumber
                        }

                        val updatedUser = User(
                            name = name,
                            email = email,
                            mobilenumber = mobileNumber,
                            address = address,
                            wpnumber = wpNumber
                        )

                        dbViewModel.updateUserDetails(updatedUser, context)
                        sharedViewModel.fetchUserDetails()
                    }
                    isEditing = !isEditing
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.padding(top = MaterialTheme.dimens.small1)
            ) {
                Text(
                    text = if (isEditing) "Save" else "Edit",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun EditableProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardType: KeyboardType,
    imageVector: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = hint,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            cursorColor = MaterialTheme.colorScheme.secondary
        )
    )
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

