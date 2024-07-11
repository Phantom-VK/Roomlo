package com.example.roomlo.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.roomlo.ui.theme.interFont

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppSearchBar(searchQuery: String) {
    var searchQuery1 = remember { mutableStateOf(searchQuery) }

    SearchBar(
        query = searchQuery1.value,
        onQueryChange = {
            searchQuery1.value = it

        },
        onSearch = { /* Perform search */ },
        active = false,
        onActiveChange = {},
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.primary
            )

        ),
        placeholder = {
            Text(text = "Search Room", color = MaterialTheme.colorScheme.primary)
        },
        shape = RoundedCornerShape(30.dp),
        trailingIcon = {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.secondary
                ),

                ) {
                Text(
                    text = "Filters",
                    fontFamily = interFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Filters",
                    tint = MaterialTheme.colorScheme.primary
                )

            }
        }
    ) {
        // Optional: Add additional content inside the search bar
    }

}