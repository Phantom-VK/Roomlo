package com.example.roomlo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomlo.ui.theme.interFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: RoomViewModel
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(title = "RoomLo",
                onTrailingIconClicked = {
                    navController.navigate(Screen.ProfileScreen.route)
                })
        },
        containerColor = Color.White,
        bottomBar = { AppBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .border(
                        BorderStroke(2.dp, Color.DarkGray),
                        shape = RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center

            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { /* Perform search */ },
                    active = false,
                    onActiveChange = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    },
                    colors = SearchBarDefaults.colors(
                        Color.White
                    ),
                    placeholder = {
                        Text(text = "Search Room")
                    },
                    shape = RoundedCornerShape(30.dp),
                    trailingIcon = {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                Color.White
                            ),

                            ) {
                            Text(
                                text = "Filters",
                                fontFamily = interFont,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp
                            )
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Filters"
                            )

                        }
                    }
                ) {
                    // Optional: Add additional content inside the search bar
                }
            }




            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    RoomItemView()
                }
            }
        }
    }
}
