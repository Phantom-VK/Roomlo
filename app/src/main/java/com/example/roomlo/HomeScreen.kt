package com.example.roomlo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: RoomViewModel

) {
    Scaffold(
        topBar = {
            AppTopBar(title = "RoomLo",
                onTrailingIconClicked = {
                    navController.navigate(Screen.ProfileScreen.route)
                })
        },
        containerColor = Color.White,
        bottomBar = { AppBottomBar()}


    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

        }
    }
}