package com.example.roomlo.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.roomlo.R
import com.example.roomlo.ui.theme.baloo
import com.example.roomlo.ui.theme.dimens
import com.example.roomlo.viewmodels.SharedViewModel
import com.example.roomlo.viewmodels.UserProfileViewModel
import com.google.firebase.auth.userProfileChangeRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClicked: () -> Unit = {},
    profileViewModel: UserProfileViewModel,
    sharedViewModel: SharedViewModel

) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.Black, Color.Gray)
    )


    val user by sharedViewModel.userDetails.collectAsState()
    val profilePictureUrl = user?.profileImageUrl

    val navigationIcon: (@Composable () -> Unit) = {
        if (!title.contains("Roomlo")) {
            IconButton(onClick = { onLeadingIconClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

        } else {
            IconButton(onClick = { onLeadingIconClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

    }

    val profileIcon: @Composable (RowScope.() -> Unit) = {
        if (!title.contains("Profile")) {
            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimens.logoSize)
                    .clip(CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .clickable {
                        onTrailingIconClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = profilePictureUrl,
                    modifier = Modifier
                        .size(MaterialTheme.dimens.logoSize)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = rememberVectorPainter(image = Icons.Default.AccountCircle),
                    error = rememberVectorPainter(image = Icons.Default.AccountCircle),
                    contentDescription = null,
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
