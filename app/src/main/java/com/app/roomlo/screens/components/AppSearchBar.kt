package com.app.roomlo.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.roomlo.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier : Modifier = Modifier,
    searchQuery : String,
    onTextChange : (String) -> Unit ={},

){
    SearchBar(
        query = searchQuery,
        onQueryChange = onTextChange,
        onSearch = { /* TODO */ },
        active = false,
        onActiveChange = { /* TODO */ },
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .padding(
                start = MaterialTheme.dimens.small2,
                top = 2.dp,
                end = MaterialTheme.dimens.small2)
            .fillMaxWidth()
            .height(MaterialTheme.dimens.logoSize+12.dp)  // Adjusted height
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(30.dp)
            ),
        placeholder = {
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text("Search")
            }
        },
        leadingIcon = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        tonalElevation = 0.dp
    ) {
        // Search suggestions or results
    }



}



