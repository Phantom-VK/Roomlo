package com.example.roomlo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomlo.screens.components.AppSearchBar
import com.example.roomlo.screens.components.RoomItemView
import com.example.roomlo.viewmodels.RoomViewModel

@Composable
fun HomeView(paddingValues: PaddingValues, viewModel:RoomViewModel){
    Column(
        modifier =Modifier
            .padding(paddingValues)
            .fillMaxSize()
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