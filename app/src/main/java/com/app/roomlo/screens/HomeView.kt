package com.app.roomlo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.roomlo.screens.components.AppSearchBar
import com.app.roomlo.screens.components.RoomItemView
import com.app.roomlo.viewmodels.RoomViewModel

@Composable
fun HomeView(paddingValues: PaddingValues){
    val viewModel:RoomViewModel = hiltViewModel<RoomViewModel>()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        AppSearchBar(viewModel.searchQuery)
        Spacer(modifier = Modifier.height(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                RoomItemView()
                RoomItemView()
                RoomItemView()
                RoomItemView()
                RoomItemView()
                RoomItemView()
                RoomItemView()
                RoomItemView()
            }
        }
    }

}