package com.example.roomlo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomlo.ui.theme.baloo
import com.example.roomlo.ui.theme.dimens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: RoomViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var wpNumber by remember { mutableStateOf("") }

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
            modifier = Modifier.padding(MaterialTheme.dimens.small1)
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.requiredSize(150.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.requiredSize(110.dp),
                    tint = MaterialTheme.colorScheme.background
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Vikramaditya Khupse",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                color = MaterialTheme.colorScheme.background
            )

            UnderlineTextField(
                value = name,
                onValueChange = { name = it },
                hint = "Name",
                imageVector = Icons.Filled.AccountCircle
            )
            UnderlineTextField(
                value = address,
                onValueChange = { address = it },
                hint = "Address",
                imageVector = Icons.Filled.LocationOn
            )
            UnderlineTextField(
                value = email,
                onValueChange = { email = it },
                hint = "Email ID",
                imageVector = Icons.Outlined.Email
            )
            UnderlineTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                hint = "Phone Number",
                imageVector = Icons.Filled.Call
            )
            UnderlineTextField(
                value = wpNumber,
                onValueChange = { wpNumber = it },
                hint = "WhatsApp Number (Optional)",
                imageVector = Icons.Filled.Call
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small2),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Userid & Password", color = MaterialTheme.colorScheme.secondary)
            }

            Button(
                onClick = { /*TODO*/ },
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
    imageVector: ImageVector
) {
    var isHintDisplayed by remember { mutableStateOf(value.isEmpty()) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.dimens.small1)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = hint,
            modifier = Modifier.requiredSize(MaterialTheme.dimens.medium3),
            tint = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small2))
        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    isHintDisplayed = it.isEmpty()
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.small1,
                        bottom = MaterialTheme.dimens.small1
                    )
            ) { innerTextField ->
                Column {
                    if (isHintDisplayed) {
                        Text(
                            text = hint,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize
                            )
                        )
                    }
                    innerTextField()
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(top = MaterialTheme.dimens.small1)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(viewModel = RoomViewModel(), navController = NavController(LocalContext.current))
}
