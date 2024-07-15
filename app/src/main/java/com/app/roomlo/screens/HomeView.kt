package com.app.roomlo.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.roomlo.screens.components.AppSearchBar
import com.app.roomlo.screens.components.RoomItemView
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont
import com.app.roomlo.viewmodels.PropertyViewModel


@Composable
fun HomeView(paddingValues: PaddingValues) {
    val viewModel: PropertyViewModel = hiltViewModel()
    val searchQuery by remember {
        mutableStateOf("")
    }
    val itemCount  = remember {
        mutableIntStateOf(4)
    }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        AppSearchBar(
            placeHolder = "Search",
            searchQuery = searchQuery

        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.dimens.small1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            // Filters Button
            OutlinedButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(MaterialTheme.dimens.buttonHeight)
            ) {
                Text(
                    text = "Filters",
                    fontFamily = interFont,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Light,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    color = MaterialTheme.colorScheme.secondary
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Filters",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            // Rent Button
            OutlinedButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(MaterialTheme.dimens.buttonHeight)
            ) {
                Text(
                    text = "Rent",
                    fontFamily = interFont,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Light,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    color = MaterialTheme.colorScheme.secondary
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Rent",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            // City Text Button
            TextButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(MaterialTheme.dimens.buttonHeight)
            ) {
                Text(
                    text = "City",
                    fontFamily = interFont,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Light,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    color = MaterialTheme.colorScheme.secondary,
                    textDecoration = TextDecoration.Underline
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "City",
                    tint = MaterialTheme.colorScheme.secondary,

                    )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TextButton(onClick = { /*TODO*/ },
                modifier = Modifier.height(MaterialTheme.dimens.buttonHeight)) {
                Text(
                    text = "20 Rooms",
                    fontFamily = interFont,
                    fontWeight = FontWeight.Light,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDown,
                    contentDescription = "Room Count",
                    tint = MaterialTheme.colorScheme.secondary,

                    )

            }

        }


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(6) {
                RoomItemView()
            }
        }


    }
}




@Preview(showBackground = true)
@Composable
fun HomeViewPreview(){
    HomeView(paddingValues = PaddingValues())
}
