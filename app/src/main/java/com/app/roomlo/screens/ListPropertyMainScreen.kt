package com.app.roomlo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.navigation.Screen
import com.app.roomlo.screens.components.AppSearchBar
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont

@Composable
fun ListPropertyScaffoldScreen(navController: NavController) {
    var progress by remember { mutableFloatStateOf(0.33f) }
    val property = remember { Property() }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController)
            HorizontalDivider(thickness = 3.dp, modifier = Modifier.fillMaxWidth())
            ScreenTitle(Screen.ListPropertyCurrentScreen)
            ScreenTabs(Screen.ListPropertyCurrentScreen, progress) { newScreen, newProgress ->
                Screen.ListPropertyCurrentScreen.route = newScreen.route
                progress = newProgress
            }
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
            )

            when (Screen.ListPropertyCurrentScreen.route) {
                Screen.ListPropertyAddressView.route -> AddressScreen(property, navController)
                Screen.ListPropertyDetailsView.route -> PropertyDetailsFormScreen(property, navController)
                Screen.ListPropertyImagesView.route -> PropertyImagesUploadView(property, navController)
                else -> AddressScreen(property, navController)
            }
        }
    }
}

@Composable
fun AddressScreen(property: Property, navController: NavController) {
    var city by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1)
    ) {
        AddressTextField(value = city, placeholder = "City", onValueChange = { city = it })
        AddressTextField(value = locality, placeholder = "Locality", onValueChange = { locality = it })
        AddressTextField(value = landmark, placeholder = "Landmark/Street", onValueChange = { landmark = it })
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.buttonHeight + 15.dp))
        HorizontalDivider(thickness = 3.dp, modifier = Modifier.fillMaxWidth())

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
        AppSearchBar(searchQuery = searchQuery)

        // TODO: Placeholder for map

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    property.address = "$city, $locality, $landmark"
                    navController.navigate(Screen.ListPropertyDetailsView.route)
                },
                modifier = Modifier.padding(MaterialTheme.dimens.small2),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Save & Continue", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun PropertyDetailsFormScreen(property: Property, navController: NavController) {
    // Form fields here...
    // Example field handling
    var roomPrice by remember { mutableStateOf("") }
    var roomSize by remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = MaterialTheme.dimens.small1)
    ) {


        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = roomPrice,
                    onValueChange = { roomPrice = it },
                    label = { Text("Room Price") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
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

                OutlinedTextField(
                    value = roomSize,
                    onValueChange = { roomSize = it },
                    label = { Text("Room Size") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
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
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, end = 5.dp)
            )
        }

        // Additional fields...

        item {
            OutlinedButton(
                onClick = {
                    property.rent = roomPrice.toIntOrNull().toString()
                    property.size = roomSize.toIntOrNull().toString()
                    navController.navigate(Screen.ListPropertyImagesView.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.small2,
                        start = MaterialTheme.dimens.small2,
                        end = MaterialTheme.dimens.small2
                    )
                    .height(MaterialTheme.dimens.buttonHeight)
            ) {
                Text("Save and Continue")
            }
        }
    }
}

@Composable
fun PropertyImagesUploadView(property: Property, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small1)
    ) {
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Text(
            text = "Upload Photos & Videos",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Upload images and videos to help buyers visualize the property.",
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        // TODO: Add image and video upload components
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // Save the property and move to the next screen or finish
                },
                modifier = Modifier.padding(MaterialTheme.dimens.small2),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Save & Continue", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Property Listing",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            TextButton(onClick = { /* Add logic */ }) {
                Text(text = "Help", color = MaterialTheme.colorScheme.secondary)
            }
        }
    )
}

@Composable
fun ScreenTitle(currentScreen: Screen) {
    // Display the title based on the current screen
    Text(
        text = when (currentScreen.route) {
            Screen.ListPropertyAddressView.route -> "Property Address"
            Screen.ListPropertyDetailsView.route -> "Property Details"
            Screen.ListPropertyImagesView.route -> "Upload Photos & Videos"
            else -> "Property Address"
        },
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun ScreenTabs(currentScreen: Screen, progress: Float, onTabSelected: (Screen, Float) -> Unit) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            TabItem(
                screen = Screen.ListPropertyAddressView,
                isSelected = currentScreen == Screen.ListPropertyAddressView,
                onClick = { onTabSelected(Screen.ListPropertyAddressView, 0.33f) }
            )
        }
        item {
            TabItem(
                screen = Screen.ListPropertyDetailsView,
                isSelected = currentScreen == Screen.ListPropertyDetailsView,
                onClick = { onTabSelected(Screen.ListPropertyDetailsView, 0.66f) }
            )
        }
        item {
            TabItem(
                screen = Screen.ListPropertyImagesView,
                isSelected = currentScreen == Screen.ListPropertyImagesView,
                onClick = { onTabSelected(Screen.ListPropertyImagesView, 1.0f) }
            )
        }
    }
}

@Composable
fun TabItem(screen: Screen, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = screen.route,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun AddressTextField(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
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
fun ListPropertyPreview(){
    ListPropertyScaffoldScreen(navController = rememberNavController())
}