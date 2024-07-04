package com.example.roomlo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    val searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(title = "RoomLo",
                onTrailingIconClicked = {
                    navController.navigate(Screen.ProfileScreen.route)
                })
        },
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { AppBottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            AppSearchBar(searchQuery)
            Spacer(modifier = Modifier.height(5.dp))
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppSearchBar(searchQuery: String) {
    var searchQuery1 = searchQuery
    
        SearchBar(
            query = searchQuery1,
            onQueryChange = { searchQuery1 = it },
            onSearch = { /* Perform search */ },
            active = false,
            onActiveChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            colors = SearchBarDefaults.colors(
                MaterialTheme.colorScheme.primary
            ),
            placeholder = {
                Text(text = "Search Room")
            },
            shape = RoundedCornerShape(30.dp),
            trailingIcon = {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primary
                    ),

                    ) {
                    Text(
                        text = "Filters",
                        fontFamily = interFont,
                        fontWeight = FontWeight.Medium,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize
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
