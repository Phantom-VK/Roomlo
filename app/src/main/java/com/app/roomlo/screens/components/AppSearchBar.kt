package com.app.roomlo.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.roomlo.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier : Modifier = Modifier,
    searchQuery : String,
    onTextChange : (String) -> Unit ={},

) {
    val colors1 = SearchBarDefaults.colors(
        containerColor = MaterialTheme.colorScheme.primary,
    )
    // Search suggestions or results
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = onTextChange,
                onSearch = { /* TODO */ },
                expanded = false,
                onExpandedChange = {},
                enabled = true,
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
                trailingIcon = null,
                colors = colors1.inputFieldColors,
                interactionSource = null,
            )
        },
        expanded = false,
        onExpandedChange = { },
        modifier = modifier
            .padding(
                start = MaterialTheme.dimens.small2,
                top = 2.dp,
                end = MaterialTheme.dimens.small2
            )
            .fillMaxWidth()
            .height(MaterialTheme.dimens.logoSize + 12.dp)  // Adjusted height
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(30.dp)
            ),
        shape = RoundedCornerShape(30.dp),
        colors = colors1,
        tonalElevation = 0.dp,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets
    ){

    }


}



