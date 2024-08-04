package com.app.roomlo.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.roomlo.R
import com.app.roomlo.navigation.Screen
import com.app.roomlo.ui.theme.dimens

@Composable
fun AppBottomBar(onItemSelected: (Screen) -> Unit) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.HomeView
        ),
        BottomNavigationItem(
            title = "Map",
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon = Icons.Outlined.LocationOn,
            route = Screen.MapView
        ),
        BottomNavigationItem(
            title = "Wishlist",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = ImageVector.vectorResource(R.drawable.baseline_favorite_border_24),
            route = Screen.WishlistView
        ),
        BottomNavigationItem(
            title = "Property",
            selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sensor_door_24),
            unselectedIcon = ImageVector.vectorResource(R.drawable.outline_sensor_door_24),
            route = Screen.PropertyView
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(MaterialTheme.dimens.logoSize+23.dp)
            .border(2.dp, MaterialTheme.colorScheme.surface)) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    onItemSelected(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = "${item.title} Button",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.requiredSize(MaterialTheme.dimens.medium2)
                    )
                },
                label = { Text(text = item.title)}
            )
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Screen
)

@Preview
@Composable
fun BottomBarPreview(){
    AppBottomBar({})
}