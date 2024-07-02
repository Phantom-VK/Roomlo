package com.example.roomlo

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomlo.ui.theme.baloo2Font

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBackNavClicked: () -> Unit = {},
    onTrailingIconClicked: ()->Unit = {},

) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val navigationIcon: (@Composable () -> Unit) = {
        if (!title.contains("RoomLo")) {
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = Color.White
                )
            }
        } else {
            IconButton(onClick = {/*TODO*/}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier.requiredSize(40.dp)
                )

            }
        }

    }

    val profileIcon: @Composable (RowScope.() -> Unit) ={
        if (!title.contains("Profile")){
            IconButton(onClick = {  onTrailingIconClicked()  }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Localized description",
                    Modifier.requiredSize(40.dp),
                    tint = Color.Black
                )
            }
        }else{
            null
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black

        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = baloo2Font,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        },
        navigationIcon = navigationIcon,
        actions = profileIcon,
        scrollBehavior = scrollBehavior
    )
}
