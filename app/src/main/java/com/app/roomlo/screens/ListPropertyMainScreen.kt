package com.app.roomlo.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.roomlo.R
import com.app.roomlo.dataclasses.LocationData
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.navigation.Screen
import com.app.roomlo.repository.LocationUtils
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.LocationViewModel
import com.app.roomlo.viewmodels.PropertyViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import java.util.Locale


@Composable
fun ListPropertyScaffoldScreen(navController: NavController, preferenceHelper: PreferenceHelper) {
    var currentScreen by remember { mutableStateOf(Screen.ListPropertyAddressView) }
    val property = remember { mutableStateOf(Property()) }
    val viewModel: LocationViewModel = hiltViewModel()
    val context = LocalContext.current



    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController)
            Divider()
            ScreenTitle(currentScreen)
            ScreenTabs(currentScreen) { newScreen ->
                currentScreen = newScreen
            }
            LinearProgressIndicator(
                progress = {
                    when (currentScreen) {
                        Screen.ListPropertyAddressView -> 0.33f
                        Screen.ListPropertyDetailsView -> 0.66f
                        Screen.ListPropertyImagesView -> 1f
                        else -> 0.0f
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSecondary,
            )

            when (currentScreen) {
                Screen.ListPropertyAddressView -> AddressScreen(property.value, currentLocation = viewModel.currentlocation.value ) {
                    currentScreen = Screen.ListPropertyDetailsView

                }

                Screen.ListPropertyDetailsView -> PropertyDetailsFormScreen(property.value) {
                    currentScreen = Screen.ListPropertyImagesView
                }

                Screen.ListPropertyImagesView -> PropertyImagesUploadView(property.value, context = context, preferenceHelper = preferenceHelper)

                else -> AddressScreen(property = property.value,currentLocation = viewModel.currentlocation.value ) {

                }
            }
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun ScreenTitle(currentScreen: Screen) {
    Text(
        text = "Add ${
            currentScreen.route.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
        }",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun ScreenTabs(currentScreen: Screen, onTabSelected: (Screen) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScreenTab("Address", currentScreen == Screen.ListPropertyAddressView) {
            onTabSelected(Screen.ListPropertyAddressView)
        }
        ScreenTab("Details", currentScreen == Screen.ListPropertyDetailsView) {
            onTabSelected(Screen.ListPropertyDetailsView)
        }
        ScreenTab("Images", currentScreen == Screen.ListPropertyImagesView) {
            onTabSelected(Screen.ListPropertyImagesView)
        }
    }
}

@Composable
fun ScreenTab(label: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = label,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun AddressScreen(property: Property, currentLocation: LocationData? = null, onNext: () -> Unit) {
    // Use rememberSaveable to retain UI state during recomposition
    var city by rememberSaveable { mutableStateOf(property.city) }
    var locality by rememberSaveable { mutableStateOf(property.locality) }
    var landmark by rememberSaveable { mutableStateOf(property.landmark) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val currentLatitude = currentLocation?.latitude ?: 0.0
    val currentLongitude = currentLocation?.longitude ?: 0.0

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add space between items
    ) {
        item {
            AddressTextField(value = city, placeholder = "City") { city = it }
        }
        item {
            AddressTextField(value = locality, placeholder = "Locality") { locality = it }
        }
        item {
            AddressTextField(value = landmark, placeholder = "Landmark/Street") { landmark = it }
        }
        item {
            Divider()
        }
        item {
            Text(
                text = "Mark your Property on Map",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        item {
            Text(
                text = "Set property location by using the search box and move the map",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        item {
            AddressTextField(value = searchQuery, placeholder = "Search location") { searchQuery = it }
        }
        item {
            MapScreen(viewModel = hiltViewModel<LocationViewModel>(), currentLatitude, currentLongitude, onLocationSelected = { lat, lng ->
                property.latitude = lat
                property.longitude = lng
            })
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Button(
                onClick = {
                    property.apply {
                        this.city = city
                        this.locality = locality
                        this.landmark = landmark
                        address = "$city, $locality, $landmark"
                    }
                    onNext()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimens.small3)
                    .height(MaterialTheme.dimens.logoSize),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text("Save & Continue", color = Color.White)
            }
        }
    }
}


@Composable
fun PropertyDetailsFormScreen(property: Property, onNext: () -> Unit) {
    val propertyName = remember { mutableStateOf("") }
    val propertyRent = remember { mutableStateOf("") }
    val propertySize = remember { mutableStateOf("") }
    val selectedFloor = remember { mutableStateOf(property.floor) }
    val selectedDeposit = remember { mutableStateOf(property.deposit) }
    val selectedMaintenance = remember { mutableStateOf(property.maintenance) }
    val selectedElectricBill = remember { mutableStateOf(property.electricbill) }
    val selectedParking = remember { mutableStateOf(property.parking) }
    val selectedNonveg = remember { mutableStateOf(property.nonveg) }
    val selectedWifi = remember { mutableStateOf(property.wifi) }
    val selectedSharingType = remember { mutableStateOf(property.sharingType) }
    val selectedHouseType = remember { mutableStateOf(property.housetype) }
    val selectedRoomType = remember { mutableStateOf(property.roomtype) }
    val selectedAvailableFor = remember { mutableStateOf(property.availablefor) }
    val selectedBalcony = remember { mutableStateOf(property.balcony) }
    val selectedBathroom = remember { mutableStateOf(property.bathroom.toString()) }
    val selectedToilet = remember { mutableStateOf(property.toilet.toString()) }
    val selectedAmenities = remember { mutableStateOf(property.amenities) }
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Property Name") },
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
                ),
                value = propertyName.value,
                onValueChange = { propertyName.value = it
                    property.propertyName = propertyName.value}
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
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
                    ),
                    value = propertyRent.value,
                    onValueChange = { propertyRent.value = it
                                    property.rent = propertyRent.value},
                    label = { Text("Property Rent") },
                )
                OutlinedTextField(
                    value = propertySize.value,
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
                    ),
                    onValueChange = { propertySize.value = it
                                    property.size = propertySize.value},
                    label = { Text("Property Size") },
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            SectionWithOptions(
                label = "Floor:",
                options = listOf("1st", "2nd", "3rd", "4th", "5th", "Custom"),
                selectedOption = selectedFloor.value
            ) { selectedOption ->
                selectedFloor.value = selectedOption
                property.floor = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Deposit:",
                options = listOf("1 Month", "2 Months", "3 Months", "Custom"),
                selectedOption = selectedDeposit.value
            ) { selectedOption ->
                selectedDeposit.value = selectedOption
                property.deposit = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Maintenance:",
                options = listOf("Included", "Excluded", "Custom"),
                selectedOption = selectedMaintenance.value
            ) { selectedOption ->
                selectedMaintenance.value = selectedOption
                property.maintenance = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Electricity Bill:",
                options = listOf("Included", "Separate"),
                selectedOption = selectedElectricBill.value
            ) { selectedOption ->
                selectedElectricBill.value = selectedOption
                property.electricbill = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Parking:",
                options = listOf("Bike", "Car", "Both", "None"),
                selectedOption = selectedParking.value
            ) { selectedOption ->
                selectedParking.value = selectedOption
                property.parking = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Non-veg:",
                options = listOf("Allowed", "Not Allowed"),
                selectedOption = selectedNonveg.value
            ) { selectedOption ->
                selectedNonveg.value = selectedOption
                property.nonveg = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Wifi:",
                options = listOf("Available", "Not Available"),
                selectedOption = selectedWifi.value
            ) { selectedOption ->
                selectedWifi.value = selectedOption
                property.wifi = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Sharing Type:",
                options = listOf("Single", "Double", "Triple", "Custom"),
                selectedOption = selectedSharingType.value
            ) { selectedOption ->
                selectedSharingType.value = selectedOption
                property.sharingType = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "House Type:",
                options = listOf("Apartment", "Independent House", "Villa", "Custom"),
                selectedOption = selectedHouseType.value
            ) { selectedOption ->
                selectedHouseType.value = selectedOption
                property.housetype = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Room Type:",
                options = listOf("1BHK", "2BHK", "3BHK", "Custom"),
                selectedOption = selectedRoomType.value
            ) { selectedOption ->
                selectedRoomType.value = selectedOption
                property.roomtype = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Available For:",
                options = listOf("Family", "Bachelor", "Anyone"),
                selectedOption = selectedAvailableFor.value
            ) { selectedOption ->
                selectedAvailableFor.value = selectedOption
                property.availablefor = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Balcony:",
                options = listOf("Yes", "No"),
                selectedOption = selectedBalcony.value
            ) { selectedOption ->
                selectedBalcony.value = selectedOption
                property.balcony = selectedOption
            }
        }
        item {
            SectionWithOptions(
                label = "Bathroom:",
                options = listOf("1", "2", "3", "Custom"),
                selectedOption = selectedBathroom.value
            ) { selectedOption ->
                selectedBathroom.value = selectedOption
                property.bathroom = selectedOption.toIntOrNull() ?: 0
            }
        }
        item {
            SectionWithOptions(
                label = "Toilet:",
                options = listOf("1", "2", "3", "Custom"),
                selectedOption = selectedToilet.value
            ) { selectedOption ->
                selectedToilet.value = selectedOption
                property.toilet = selectedOption.toIntOrNull() ?: 0
            }
        }
        item {
            MultiSelectionSection(
                label = "Amenities:",
                options = listOf(
                    "AC",
                    "TV",
                    "Fridge",
                    "Washing Machine",
                    "Microwave",
                    "Gas Stove",
                    "Custom"
                ),
                selectedOptions = selectedAmenities.value
            ) { amenity ->
                val updatedAmenities = if (selectedAmenities.value.contains(amenity)) {
                    selectedAmenities.value - amenity
                } else {
                    selectedAmenities.value + amenity
                }
                selectedAmenities.value = updatedAmenities
                property.amenities = updatedAmenities
            }
        }

        item {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimens.small3)
                    .height(MaterialTheme.dimens.logoSize),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text("Save and Continue", color = Color.White)
            }
        }
    }
}

@Composable
fun SectionWithOptions(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(options) { option ->
            if (option == "Custom" && selectedOption !in options) {
                CustomOptionWithTextField(
                    textFieldPlaceholder = "Custom",
                    initialValue = selectedOption,
                    onValueChange = onOptionSelected
                )
            } else {
                OptionButton(
                    text = option,
                    isSelected = selectedOption == option,
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}

@Composable
fun MultiSelectionSection(
    label: String,
    options: List<String>,
    selectedOptions: List<String>,
    onOptionSelected: (String) -> Unit
) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(options) { option ->
            if (option == "Custom") {
                CustomOptionWithTextField(
                    textFieldPlaceholder = "Custom",
                    initialValue = "",
                    onValueChange = onOptionSelected
                )
            } else {
                OptionButton(
                    text = option,
                    isSelected = selectedOptions.contains(option),
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}

@Composable
fun OptionButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.wrapContentSize(),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.secondary,
            containerColor = if (isSelected) Color.Blue else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text, fontSize = 14.sp)
    }
}

@Composable
fun CustomOptionWithTextField(
    textFieldPlaceholder: String,
    initialValue: String,
    onValueChange: (String) -> Unit
) {
    //TODO Take care of custom option bug
    var value by remember { mutableStateOf(initialValue) }
    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = { Text(textFieldPlaceholder, fontSize = 14.sp) },
        modifier = Modifier.width(120.dp),
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
fun PropertyImagesUploadView(property: Property, context: Context, preferenceHelper: PreferenceHelper) {
    var listOfPhotos by remember { mutableStateOf(property.propertyImages.map { Uri.parse(it) }) }
    val propertyVM: PropertyViewModel = hiltViewModel()
    var isSubmitting by remember { mutableStateOf(false) }

    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(4)
    ) { uris ->
        if (uris.isNotEmpty()) {
            listOfPhotos = uris
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Upload Photos & Videos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Upload images and videos to help buyers visualize the property.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddPhotoButton(pickMultipleMedia)

        Spacer(modifier = Modifier.height(16.dp))

        LazyImageGrid(uriList = listOfPhotos.map { it.toString() })

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                isSubmitting = true
                val updatedProperty = property.copy(propertyImages = listOfPhotos.map { it.toString() },
                    owner = preferenceHelper.username,
                    ownerId = preferenceHelper.userId.toString() ,
                    ownerEmail = preferenceHelper.useremail,
                    ownerMobNo = preferenceHelper.mobilenumber)

                //TODO handle error, images are not getting upploaded also navigate to list property screen
                propertyVM.addPropertyToDatabase(updatedProperty, context = context)
                propertyVM.uploadPropertyImages(property, context)
                isSubmitting = !isSubmitting

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.small3)
                .height(MaterialTheme.dimens.logoSize),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
            enabled = !isSubmitting
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Submit Listing", color = Color.White)
            }
        }
    }


}

@Composable
fun AddressTextField(value: String, placeholder: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        value = value,
        onValueChange = onValueChange,

        label = { Text(placeholder) },

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
fun AddPhotoButton(pickMultipleMedia: ManagedActivityResultLauncher<PickVisualMediaRequest, List<Uri>>) {
    Row(modifier = Modifier.wrapContentSize()) {
        IconButton(onClick = {
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_a_photo_24),
                contentDescription = "Add Photo",
                modifier = Modifier.size(MaterialTheme.dimens.logoSize)
            )
        }
    }
}

@Composable
fun LazyImageGrid(uriList: List<String>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.dimens.medium2)
    ) {
        items(uriList) { uri ->
            Box(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.small1)
                    .size(100.dp)
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = "Property Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun MapScreen(
    viewModel: LocationViewModel,
    initialLatitude: Double,
    initialLongitude: Double,
    onLocationSelected: (latitude: Double, longitude: Double) -> Unit
) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    locationUtils.requestLocationUpdates(viewModel)

    var selectedLocation by remember { mutableStateOf(
        if (initialLatitude != 0.0 && initialLongitude != 0.0) {
            LatLng(initialLatitude, initialLongitude)
        } else {
            viewModel.location.value?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(0.0, 0.0)
        }
    ) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedLocation, 15f)
    }
    //TODO fix dark cricle around map pin
    val isDarkTheme = isSystemInDarkTheme()
    val mapStyle = if (isDarkTheme) R.raw.map_dark_style else R.raw.map_light_style

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp)),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    LocalContext.current, mapStyle
                )
            ),
            onMapClick = { latLng ->
                selectedLocation = latLng
                onLocationSelected(latLng.latitude, latLng.longitude)
            }
        ) {
            Marker(state = rememberMarkerState(position = selectedLocation))
            Circle(
                center = selectedLocation,
                radius = 200.0,
                strokeColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 2f,
                fillColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}


