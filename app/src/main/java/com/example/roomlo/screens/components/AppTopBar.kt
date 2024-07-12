package com.example.roomlo.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.roomlo.R
import com.example.roomlo.ui.theme.baloo
import com.example.roomlo.ui.theme.dimens
import com.example.roomlo.viewmodels.UserProfileViewModel
import com.google.firebase.auth.userProfileChangeRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClicked: () -> Unit = {},
    profileViewModel: UserProfileViewModel

) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.Black, Color.Gray)
    )


    val profilePictureUrl by profileViewModel.profilePictureUrl.collectAsState()

    val navigationIcon: (@Composable () -> Unit) = {
        if (!title.contains("RoomLo")) {
            IconButton(onClick = { onLeadingIconClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

        } else {
            IconButton(onClick = { onLeadingIconClick() },
                modifier = Modifier
                    .size(MaterialTheme.dimens.medium3)
                    .clip(CircleShape),

                ) {

                Icon(
                    painter = rememberAsyncImagePainter(
                        model = profilePictureUrl,
                        contentScale = ContentScale.Crop

                    ), contentDescription ="Profile Image"
                )


            }
        }

    }

    val profileIcon: @Composable (RowScope.() -> Unit) = {
        if (!title.contains("Profile")) {
            IconButton(onClick = { onTrailingIconClicked() }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Localized description",
                    Modifier.requiredSize(MaterialTheme.dimens.logoSize),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            null
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.secondary

        ),
        modifier = Modifier.background(gradientBrush),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.keyholeiconcircle),
                    //TODO add proper icon
                    contentDescription = "Roomlo",
                    Modifier.size(MaterialTheme.dimens.logoSize)
                )

                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = baloo,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize
                )
            }

        },
        navigationIcon = navigationIcon,
        actions = profileIcon,
        scrollBehavior = scrollBehavior
    )
}
