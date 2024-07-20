package com.app.roomlo.screens

import android.net.Uri
import android.util.Log
import android.widget.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.roomlo.R
import com.app.roomlo.screens.components.UnderlineTextField
import com.app.roomlo.ui.theme.baloo
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.AuthState

@Composable
fun ListPropertyScreen(navController: NavController) {
    var propertyName by remember { mutableStateOf("") }
    var propertyAddress by remember { mutableStateOf("") }
    var rent by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var extraDescription by remember { mutableStateOf("") }
    var listOfPhotos by remember { mutableStateOf(listOf<Uri>()) }

    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(4)
    ) { uris ->
        listOfPhotos = uris.takeIf { it.isNotEmpty() } ?: listOfPhotos
        Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(navController)

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.small1,
                end = MaterialTheme.dimens.small1
            )
        ){
            PropertyDetailField(
                value = propertyName,
                onValueChange = { propertyName = it },
                hint = "Property Name",
                imageVector = Icons.Filled.Home
            )
            PropertyDetailField(
                value = propertyAddress,
                onValueChange = { propertyAddress = it },
                hint = "Address",
                imageVector = Icons.Filled.LocationOn
            )
            PropertyDetailField(
                value = rent,
                onValueChange = { rent = it },
                hint = "Rent",
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_currency_rupee_24)
            )
            PropertyDetailField(
                value = size,
                onValueChange = { size = it },
                hint = "Size (Sq Ft)",
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_crop_square_24)
            )
            PropertyDetailField(
                value = extraDescription,
                onValueChange = { extraDescription = it },
                hint = "More Details",
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_more_24)
            )
        }


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

        LazyImageGrid(uriList = listOfPhotos)
        Column (
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Button(
                onClick = {
                    //TODO
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( bottom = MaterialTheme.dimens.small3)
                    .height(MaterialTheme.dimens.logoSize),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(text = "Upload Property", color = MaterialTheme.colorScheme.primary)
            }
        }

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



@Preview(showBackground = true)
@Composable
fun ListPropertyScreenPreview() {
    ListPropertyScreen(navController = NavController(LocalContext.current))
}