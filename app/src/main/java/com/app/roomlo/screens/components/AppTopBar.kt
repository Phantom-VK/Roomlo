package com.app.roomlo.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.roomlo.ui.theme.baloo
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.SharedViewModel
import com.app.roomlo.viewmodels.UserProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClicked: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    val sharedViewModel = hiltViewModel<SharedViewModel>()
    val user by sharedViewModel.userDetails.collectAsState()
    val profilePictureUrl = user?.profileImageUrl

    val navigationIcon: (@Composable () -> Unit) = {
        if (!title.contains("Roomlo")) {
            IconButton(onClick = { onLeadingIconClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

        } else {
            IconButton(onClick = { onLeadingIconClick() },
                modifier = Modifier.size(MaterialTheme.dimens.logoSize-10.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(MaterialTheme.dimens.logoSize-10.dp)
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
                    .border(1.dp, MaterialTheme.colorScheme.secondary)
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


        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.secondary

        ),title = {
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
