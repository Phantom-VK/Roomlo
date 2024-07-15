package com.app.roomlo.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.roomlo.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier : Modifier = Modifier,
    searchQuery : String,
    onTextChange : (String) -> Unit ={},
    placeHolder : String,
    onCloseClicked : () -> Unit = {},
    onQueryChange : (String) -> Unit = {},
    onMicClicked : () -> Unit= {}
){
    SearchBar(
        query = searchQuery,
        // 2
        onQueryChange = onTextChange,
        // 3
        onSearch = {  },
        active = false,
        onActiveChange = {  },
        // 4
        modifier = modifier
            .padding(start = MaterialTheme.dimens.small2, top = 2.dp, MaterialTheme.dimens.small2, MaterialTheme.dimens.small2)
            .fillMaxWidth(),
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        // 5
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        tonalElevation = 0.dp
    ) {
        // Search suggestions or results
    }

}

@Composable
fun SearchBarDivider(
    modifier: Modifier = Modifier
){
    HorizontalDivider(
        modifier = modifier
            .width(1.dp),
        thickness = 20.dp,
        color = MaterialTheme.colorScheme.background
    )
}

