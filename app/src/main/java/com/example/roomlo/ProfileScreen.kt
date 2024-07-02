package com.example.roomlo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    viewModel: RoomViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppTopBar(title = "Profile") },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.requiredSize(150.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.requiredSize(180.dp),
                    tint = Color(0xFFADC9FF)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Vikramaditya Khupse",
                fontSize = 20.sp,
                color = Color.DarkGray
            )

            UnderlineTextField(
                value = name,
                onValueChange = { name = it },
                hint = "Name"
            )
            UnderlineTextField(
                value = address,
                onValueChange = { address = it },
                hint = "Address"
            )
            UnderlineTextField(
                value = email,
                onValueChange = { email = it },
                hint = "Email ID"
            )
            UnderlineTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                hint = "Phone Number"
            )

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun UnderlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String
) {
    var isHintDisplayed by remember { mutableStateOf(value.isEmpty()) }

    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isHintDisplayed = it.isEmpty()
        },
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) { innerTextField ->
        Column {
            if (isHintDisplayed) {
                Text(
                    text = hint,
                    style = TextStyle(color = Color.Gray, fontSize = 18.sp)
                )
            }
            innerTextField()
            Divider(color = Color.Gray, thickness = 2.dp)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    ProfileScreen(viewModel = RoomViewModel(), navController = NavController(LocalContext.current))
}
