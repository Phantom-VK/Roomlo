package com.app.roomlo.screens

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.roomlo.R
import com.app.roomlo.dataclasses.Property
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.screens.components.UnderlineTextField
import com.app.roomlo.ui.theme.baloo
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.PropertyViewModel

@Composable
fun ListPropertyScreen(navController: NavController, preferenceHelper: PreferenceHelper) {
    var propertyName by remember { mutableStateOf("") }
    var propertyAddress by remember { mutableStateOf("") }
    var rent by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var sharingType by remember { mutableStateOf("") }
    var extraDescription by remember { mutableStateOf("") }
    var listOfPhotos by remember { mutableStateOf(listOf<Uri>()) }

    val context = LocalContext.current
    val propertyVM: PropertyViewModel = hiltViewModel()

    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(4)
    ) { uris ->
        listOfPhotos = uris.takeIf { it.isNotEmpty() } ?: listOfPhotos
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(navController)
        PropertyDetailsForm(
            propertyName = propertyName,
            onPropertyNameChange = { propertyName = it },
            propertyAddress = propertyAddress,
            onPropertyAddressChange = { propertyAddress = it },
            rent = rent,
            onRentChange = { rent = it },
            size = size,
            onSizeChange = { size = it },
            sharingType = sharingType,
            onSharingTypeChange = { sharingType = it },
            extraDescription = extraDescription,
            onExtraDescriptionChange = { extraDescription = it }
        )

        AddPhotoButton(pickMultipleMedia)
        LazyImageGrid1(uriList = listOfPhotos)

        UploadButton(
            onClick = {
                uploadProperty(
                    propertyVM = propertyVM,
                    preferenceHelper = preferenceHelper,
                    propertyName = propertyName,
                    propertyAddress = propertyAddress,
                    rent = rent,
                    size = size,
                    listOfPhotos = listOfPhotos,
                    context = context,
                    navController = navController
                )
            }
        )
    }
}

@Composable
fun Header(navController: NavController) {
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
            text = "Property Details",
            fontFamily = baloo,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = MaterialTheme.dimens.small2)
        )
    }
}

@Composable
fun PropertyDetailsForm(
    propertyName: String,
    onPropertyNameChange: (String) -> Unit,
    propertyAddress: String,
    onPropertyAddressChange: (String) -> Unit,
    rent: String,
    onRentChange: (String) -> Unit,
    size: String,
    onSizeChange: (String) -> Unit,
    sharingType: String,
    onSharingTypeChange: (String) -> Unit,
    extraDescription: String,
    onExtraDescriptionChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1)
    ) {
        PropertyDetailField(
            value = propertyName,
            onValueChange = onPropertyNameChange,
            hint = "Property Name",
            imageVector = Icons.Filled.Home
        )
        PropertyDetailField(
            value = propertyAddress,
            onValueChange = onPropertyAddressChange,
            hint = "Address",
            imageVector = Icons.Filled.LocationOn
        )
        PropertyDetailField(
            value = rent,
            onValueChange = onRentChange,
            hint = "Rent",
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_currency_rupee_24)
        )
        PropertyDetailField(
            value = size,
            onValueChange = onSizeChange,
            hint = "Size (Sq Ft)",
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_crop_square_24)
        )
        PropertyDetailField(
            value = sharingType,
            onValueChange = onSharingTypeChange,
            hint = "Sharing type (Double/Triple/Single)",
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_crop_square_24)
        )
        PropertyDetailField(
            value = extraDescription,
            onValueChange = onExtraDescriptionChange,
            hint = "More Details",
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_more_24)
        )
    }
}

@Composable
fun PropertyDetailField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    imageVector: ImageVector
) {
    UnderlineTextField(
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        keyboardType = KeyboardType.Text,
        imageVector = imageVector
    )
}



@Composable
fun LazyImageGrid1(uriList: List<Uri>) {
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
fun UploadButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.dimens.small3)
                .height(MaterialTheme.dimens.logoSize),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
        ) {
            Text(text = "Upload Property", color = MaterialTheme.colorScheme.primary)
        }
    }
}

private fun uploadProperty(
    propertyVM: PropertyViewModel,
    preferenceHelper: PreferenceHelper,
    propertyName: String,
    propertyAddress: String,
    rent: String,
    size: String,
    listOfPhotos: List<Uri>,
    context: android.content.Context,
    navController: NavController
) {
    preferenceHelper.userId?.let { id ->
        val property = Property(
            owner = preferenceHelper.username,
            propertyName = propertyName,
            ownerId = id,
            address = propertyAddress,
            rent = rent,
            size = size,
            propertyImages = listOfPhotos.map { it.toString() },
        )
        propertyVM.addPropertyToDatabase(property, context)
        propertyVM.uploadPropertyImages(property, context)
    }
    navController.navigateUp()
}
