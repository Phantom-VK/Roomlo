package com.example.roomlo.screens.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.roomlo.R
import com.google.firebase.annotations.concurrent.Background

@Composable
fun ProfileImage(imageUrl: Uri?, onImageChangeClick: (newUri: Uri) -> Unit = {}) {
    val color = MaterialTheme.colorScheme

    val launcher = rememberLauncherForActivityResult(
        contract = GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onImageChangeClick(it)
        }
    }

    Box(Modifier.height(140.dp)) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(3.dp, color.secondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop ,
                placeholder = rememberVectorPainter(image = Icons.Default.AccountCircle),
                error = rememberVectorPainter(image = Icons.Default.AccountCircle),
                contentDescription = null,
            )
        }
        IconButton(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .size(35.dp)
                .padding(2.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd),
            colors = IconButtonDefaults.iconButtonColors(color.secondary),
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                tint = color.onPrimary
            )
        }
    }

}


