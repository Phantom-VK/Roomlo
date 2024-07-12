package com.example.roomlo.screens

import android.annotation.SuppressLint
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
import androidx.navigation.NavController
import com.example.roomlo.data.Permission
import com.example.roomlo.data.User
import com.example.roomlo.screens.components.ProfileImage
import com.example.roomlo.ui.theme.baloo
import com.example.roomlo.ui.theme.dimens
import com.example.roomlo.viewmodels.DatabaseViewModel
import com.example.roomlo.viewmodels.SharedViewModel
import com.example.roomlo.viewmodels.UserProfileViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    dbViewModel: DatabaseViewModel,
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel:UserProfileViewModel,
    permissions: Permission
) {
    val context = LocalContext.current


    val user by sharedViewModel.userDetails.collectAsState()

    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobilenumber by remember { mutableStateOf("") }
    var wpnumber by remember { mutableStateOf("") }

    // Update local state values when user changes
    LaunchedEffect(user) {
        user?.let { user1 ->
            name = user1.name
            address = user1.address
            email = user1.email
            mobilenumber = user1.mobilenumber
            wpnumber = user1.wpnumber
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
                ProfileImage(profileViewModel, permissions)
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
                                mobilenumber =  mobilenumber.trim()
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

@Composable
fun UnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    imageVector: ImageVector,
    keyboardType: KeyboardType
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
