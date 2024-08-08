package com.app.roomlo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.roomlo.screens.components.AppSearchBar
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont

@Composable
fun ListPropertyScaffoldView(
    navController: NavController

    ){

    var city by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf("") }
    var landstreet by remember { mutableStateOf("") }
    var currentScreen by remember { mutableStateOf("") }

    Scaffold (){
        paddingValues->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimens.medium2),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Text(
                    text = "List Property",
                    fontFamily = interFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.small2)
                )

            }
            HorizontalDivider(thickness = 3.dp)


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Add $currentScreen",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,)
            }
//            First three text fields
            AddressScreen(city, locality, landstreet)
        }
    }
}




@Composable
private fun AddressScreen(city: String, locality: String, landstreet: String) {
    var city1 = city
    var locality1 = locality
    var landstreet1 = landstreet

    var searchquery by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Column (){
            AddressTextField(
                value = city1,
                placeholder = "City",
                onValueChange = {
                    city1 = it
                })
            AddressTextField(
                value = locality1,
                placeholder = "Locality",
                onValueChange = { locality1 = it })
            AddressTextField(
                value = landstreet1,
                placeholder = "Landmark/Street",
                onValueChange = { landstreet1 = it })
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.buttonHeight+20.dp))
            HorizontalDivider(thickness = 3.dp)



        }




    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Icon(imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.onSecondary)
        Text(text = "Mark your Property on Map",
            fontFamily = interFont,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium)

    }
    Text(text = "Set property location by using search box and move the map",
        fontFamily = interFont,
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        fontWeight = FontWeight.Light)
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
    AppSearchBar(searchQuery = searchquery)


//TODO Empty space for map
    Column (
        modifier = Modifier.fillMaxSize().padding(MaterialTheme.dimens.small1),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = { /* TODO: Implement change password functionality */ },
            modifier = Modifier
                .padding(MaterialTheme.dimens.small2),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
        ) {
            Text(text = "Save & Continue", color = MaterialTheme.colorScheme.secondary)
        }
    }

}


@Composable
fun AddressTextField(value:String,placeholder:String ,onValueChange:(String)->Unit){

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        value = value ,
        label ={Text(text = placeholder)} ,
        onValueChange =onValueChange,
        placeholder = {Text(text = placeholder)},
        shape = OutlinedTextFieldDefaults.shape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            cursorColor = MaterialTheme.colorScheme.secondary
        )

        )
}




@Preview(showBackground = true)
@Composable
fun ListPropertyScaffoldViewPreview(){
    ListPropertyScaffoldView(navController = NavController(LocalContext.current))
}


