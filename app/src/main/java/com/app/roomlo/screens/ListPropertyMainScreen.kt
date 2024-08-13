package com.app.roomlo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.roomlo.navigation.Screen
import com.app.roomlo.screens.components.AppSearchBar
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont

@Composable
fun ListPropertyScaffoldScreen(
    navController: NavController

) {


    var progress by remember { mutableFloatStateOf(0.33f) }
    var currentScreen by rememberSaveable { mutableStateOf(Screen.ListPropertyDetailsView) }


    Scaffold() { paddingValues ->

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
                Text(
                    text = "Add ${currentScreen.route}",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = {

                    currentScreen = Screen.ListPropertyAddressView
                    if (progress > 0.3) {
                        progress = 0.33f
                    }

                }) {
                    Text(
                        text = "Address",
                        fontFamily = interFont,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                TextButton(onClick = {
                    currentScreen = Screen.ListPropertyDetailsView
                    if (progress != 0.66f){
                            progress = 0.66f
                        }

                }) {
                    Text(
                        text = "Details",
                        fontFamily = interFont,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                TextButton(onClick = {

                    currentScreen = Screen.ListPropertyImagesView
                    if (progress != 1f){
                        progress = 1f
                    }
                }) {
                    Text(
                        text = "Images",
                        fontFamily = interFont,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary
            )

            when(currentScreen){
                Screen.ListPropertyAddressView -> AddressScreen()
                Screen.ListPropertyDetailsView -> PropertyDetailsFormScreen()
                Screen.ListPropertyImagesView -> PropertyImagesUploadView()
                else -> AddressScreen()
            }

        }
    }
}


@Composable
fun AddressScreen() {
    var city by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf("") }
    var landstreet by remember { mutableStateOf("") }

    val searchquery by remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Column {
            AddressTextField(
                value = city,
                placeholder = "City",
                onValueChange = {
                    city = it
                })
            AddressTextField(
                value = locality,
                placeholder = "Locality",
                onValueChange = { locality = it })
            AddressTextField(
                value = landstreet,
                placeholder = "Landmark/Street",
                onValueChange = { landstreet = it })
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.buttonHeight + 15.dp))
            HorizontalDivider(thickness = 3.dp)


        }


    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Text(
            text = "Mark your Property on Map",
            fontFamily = interFont,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )

    }
    Text(
        text = "Set property location by using search box and move the map",
        fontFamily = interFont,
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
    AppSearchBar(searchQuery = searchquery)


//TODO Empty space for map
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small1),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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


//TODO create details screen
@Composable
fun PropertyDetailsFormScreen(){
    Row (
        modifier = Modifier
            .wrapContentSize()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (Modifier.weight(1f)){
            Column(

                verticalArrangement = Arrangement.Center
            ){
                Text(text = "Price:")
            }
            Spacer(modifier = Modifier.weight(0.02f))
            Column{
                TextField(value = "Floor no.", onValueChange = {})

            }
        }
        Row (Modifier.weight(1f)){

            Text(text = "Price:")

            Spacer(modifier = Modifier.weight(0.02f))
            Column{
                TextField(value = "Floor no.", onValueChange = {})

            }
        }


    }
    HorizontalDivider(thickness = 1.dp)
    Row (
        modifier = Modifier
            .wrapContentSize()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.weight(0.75f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "House Type:")
        }
        Column(modifier = Modifier.weight(1f)) {
            OutlinedRoundedButton(text = "Independent House", onClick = { TODO() })
        }
        Spacer(modifier = Modifier.weight(0.02f))
        Column (modifier = Modifier.weight(1f)){
            OutlinedRoundedButton(text = "Apartment/FLat", onClick = { TODO() })
        }

    }
    HorizontalDivider(thickness = 1.dp)
    Row (
        modifier = Modifier
            .wrapContentSize()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.weight(0.75f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Sharing Type:")
        }



        Column (modifier = Modifier.weight(1f)){
            OutlinedRoundedButton(text = "Single Sharing", onClick = { TODO() })
            OutlinedRoundedButton(text = "Tripple Sharing", onClick = { TODO() })
        }
        Column(modifier = Modifier.weight(1f)) {
            OutlinedRoundedButton(text = "Double Sharing", onClick = { TODO() })
        }

    }
    HorizontalDivider(thickness = 1.dp)
    Row (
        modifier = Modifier
            .wrapContentSize()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.weight(0.75f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "For:")
        }

        Column (modifier = Modifier.weight(1f)){
            OutlinedRoundedButton(text = "Boys Only", onClick = { TODO() })
            OutlinedRoundedButton(text = "Girls Only", onClick = { TODO() })
        }
        Column (modifier = Modifier.weight(1f)){
            OutlinedRoundedButton(text = "Both Boys & Girls", onClick = { TODO() })
            OutlinedRoundedButton(text = "Family only", onClick = { TODO() })
        }

    }
    HorizontalDivider(thickness = 1.dp)

    Row (
        modifier = Modifier
            .wrapContentSize()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier.weight(0.75f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Floor:")
        }
        Column(modifier = Modifier.weight(1f)) {
            OutlinedRoundedButton(text = "Ground Floor", onClick = { TODO() })
        }
        Spacer(modifier = Modifier.weight(0.02f))
        Column (modifier = Modifier.weight(1f)){
            TextField(value = "Floor no.", onValueChange = {})

        }

    }
    HorizontalDivider(thickness = 1.dp)




}

@Composable
fun PropertyImagesUploadView(){
    Text(text = "Property Images Here!")
}

@Composable
fun AddressTextField(value: String, placeholder: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1 - 7.dp),
        value = value,
        label = { Text(text = placeholder) },
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
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

@Composable
fun OutlinedRoundedButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(MaterialTheme.dimens.small1), // Set corner radius
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background // Button background color
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy( // Add outline with secondary color
            width = 1.dp,
        ),
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.secondary, // Text color
            fontSize = 10.sp, // Text size
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ListPropertyScaffoldViewPreview() {
    ListPropertyScaffoldScreen(navController = NavController(LocalContext.current))
}




