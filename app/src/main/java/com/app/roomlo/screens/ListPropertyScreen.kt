package com.app.roomlo.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.dataclasses.Property
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
    val propertyVM: PropertyViewModel = hiltViewModel<PropertyViewModel>()

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
            sharingType = sharingType,
            onSharingTypeChange = { sharingType = it },
            size = size,
            onSizeChange = { size = it },
            extraDescription = extraDescription,
            onExtraDescriptionChange = { extraDescription = it }
        )

        AddPhotoButton(pickMultipleMedia)
        LazyImageGrid(uriList = listOfPhotos)

        UploadButton(
            onClick = {
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
                    propertyVM.addPropertyToDatabase(
                        property,
                        context = context,
                        id
                    )
                    propertyVM.uploadPropertyImages(property, context)

                }

                navController.navigateUp()
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
    sharingType: String,
    onSharingTypeChange: (String) -> Unit,
    onSizeChange: (String) -> Unit,
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
fun LazyImageGrid(uriList: List<Uri>) {
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

//@Preview(showBackground = true)
//@Composable
//fun ListPropertyScreenPreview() {
//    ListPropertyScreen(navController = NavController(LocalContext.current),
//        preferenceHelper = PreferenceHelper())
//}
