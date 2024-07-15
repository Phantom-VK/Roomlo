package com.app.roomlo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.roomlo.screens.components.RoomItemView
import com.app.roomlo.ui.theme.dimens


@Composable
fun PropertyView(paddingValues:PaddingValues){

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.dimens.small2)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.onSecondary
                )) {
                Text(text = "List Property", color = MaterialTheme.colorScheme.primary)
            }
        }
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