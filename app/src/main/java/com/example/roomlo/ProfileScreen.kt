package com.example.roomlo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomlo.ui.theme.baloo2Font

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
    var userId by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            fontFamily = baloo2Font,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            color = Color.Black
        )
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.requiredSize(150.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.profile_pic),
                contentDescription = "Profile Picture",
                modifier = Modifier.requiredSize(110.dp),
                tint = Color(0xFFADC9FF)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Vikramaditya Khupse",
            fontSize = 20.sp,
            color = Color.DarkGray
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
//        UnderlineTextField(
//            value = userId,
//            onValueChange = { userId = it },
//            hint = "UserId",
//            imageVector = Icons.Filled.Face
//        )
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            colors = ButtonDefaults.buttonColors(
                Color(0xFFADC9FF)
            )
            ) {

                Text(text = "Userid & Password")


        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                Color.Green
            )
        ) {
            Text(text = "Save")

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
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Image",
            Modifier.requiredSize(40.dp), tint = Color.Black
    )
    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isHintDisplayed = it.isEmpty()
        },
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp),

        ) { innerTextField ->
        Column {
            if (isHintDisplayed) {
                Text(
                    text = hint,
                    style = TextStyle(color = Color.Gray, fontSize = 15.sp)
                )
            }
            innerTextField()
            Divider(color = Color.Gray, thickness = 2.dp)
        }
    }

}

}


@Preview(showBackground = true)
@Composable
fun Preview() {
    ProfileScreen(viewModel = RoomViewModel(), navController = NavController(LocalContext.current))
}
