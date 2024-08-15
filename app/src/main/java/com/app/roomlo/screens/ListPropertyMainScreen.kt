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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.roomlo.navigation.Screen
import com.app.roomlo.screens.components.AppSearchBar
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.ui.theme.interFont

@Composable
fun ListPropertyScaffoldScreen(navController: NavController) {
    var progress by remember { mutableFloatStateOf(0.33f) }
    var currentScreen by rememberSaveable { mutableStateOf(Screen.ListPropertyAddressView) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController)
            HorizontalDivider(thickness = 3.dp)
            ScreenTitle(currentScreen)
            ScreenTabs(currentScreen, progress) { newScreen, newProgress ->
                currentScreen = newScreen
                progress = newProgress
            }
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary
            )

            when (currentScreen) {
                Screen.ListPropertyAddressView -> AddressScreen()
                Screen.ListPropertyDetailsView -> PropertyDetailsFormScreen()
                Screen.ListPropertyImagesView -> PropertyImagesUploadView()

                else -> AddressScreen()
            }
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimens.medium2),
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
}

@Composable
fun ScreenTitle(currentScreen: Screen) {
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
}

@Composable
fun ScreenTabs(
    currentScreen: Screen,
    progress: Float,
    onTabSelected: (Screen, Float) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScreenTab(
            label = "Address",
            isSelected = currentScreen == Screen.ListPropertyAddressView,
            onClick = { onTabSelected(Screen.ListPropertyAddressView, 0.33f) }
        )
        ScreenTab(
            label = "Details",
            isSelected = currentScreen == Screen.ListPropertyDetailsView,
            onClick = { onTabSelected(Screen.ListPropertyDetailsView, 0.66f) }
        )
        ScreenTab(
            label = "Images",
            isSelected = currentScreen == Screen.ListPropertyImagesView,
            onClick = { onTabSelected(Screen.ListPropertyImagesView, 1f) }
        )
    }
}

@Composable
fun ScreenTab(label: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = label,
            fontFamily = interFont,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun AddressScreen() {
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
        HorizontalDivider(thickness = 3.dp)

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
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.dimens.small1),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* TODO: Handle Save & Continue */ },
                modifier = Modifier.padding(MaterialTheme.dimens.small2),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Save & Continue", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}


@Composable
fun PropertyDetailsFormScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = MaterialTheme.dimens.small1)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
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
                    value = "",
                    onValueChange = {},
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
        }

        item { Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1)) }

        item { SectionWithOptions("Floor:", listOf("1st", "2nd", "Custom")) }
        item { SectionWithOptions("Deposit:", listOf("1 Month", "2 Months", "Custom")) }
        item { SectionWithOptions("Maintenance:", listOf("Included", "Custom")) }
        item { SectionWithOptions("Electricity Bill:", listOf("Included", "Separate")) }
        item { SectionWithOptions("Parking:", listOf("Bike", "Car", "Both", "None")) }
        item { SectionWithOptions("Non-veg:", listOf("Yes", "No")) }
        item { SectionWithOptions("Wifi:", listOf("Yes", "No")) }
        item { SectionWithOptions("Facilities:", listOf("Bathroom", "Toilet", "Balcony", "Custom")) }

        item {
            OutlinedButton(
                onClick = { /* TODO: Handle Save & Continue */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.small2,
                        start = MaterialTheme.dimens.small2,
                        end = MaterialTheme.dimens.small2
                    )
                    .height(MaterialTheme.dimens.buttonHeight) // Assuming you've defined a dimension for button height
            ) {
                Text("Save and Continue")
            }
        }
    }
}

@Composable
fun SectionWithOptions(label: String, options: List<String>) {
    Text(
        text = label,
        fontFamily = interFont,
        modifier = Modifier.padding(vertical = MaterialTheme.dimens.small1)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        items(options.size) { index ->
            if (options[index] == "Custom") {
                CustomOptionWithTextField(textFieldPlaceholder = "Custom")
            } else {
                OptionButton(text = options[index])
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))

            }
        }
    }
}

@Composable
fun OptionButton(text: String) {
    OutlinedButton(
        onClick = { /* TODO: Handle click */ },
        shape = RoundedCornerShape(7.dp)
    ) {
        Text(text, fontSize = 12.sp) // Adjusted font size
    }
}

@Composable
fun CustomOptionWithTextField(textFieldPlaceholder: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text(textFieldPlaceholder, fontSize = 12.sp) },
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .width(100.dp)
            .height(48.dp), // Matching the button height
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
fun PropertyImagesUploadView() {
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
                onClick = { /* TODO: Handle Save & Continue */ },
                modifier = Modifier.padding(MaterialTheme.dimens.small2),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background)
            ) {
                Text(text = "Save & Continue", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun AddressTextField(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimens.small2)
    )
}

@Composable
fun HorizontalDivider(thickness: Dp) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        thickness = thickness,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun ListPropertyScaffoldScreenPreview() {
    ListPropertyScaffoldScreen(navController = rememberNavController())
}
