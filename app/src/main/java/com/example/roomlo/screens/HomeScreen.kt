package com.example.roomlo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomlo.screens.components.AppBottomBar
import com.example.roomlo.screens.components.AppSearchBar
import com.example.roomlo.screens.components.AppTopBar
import com.example.roomlo.screens.components.RoomItemView
import com.example.roomlo.ui.theme.dimens
import com.example.roomlo.viewmodels.RoomViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: RoomViewModel
) {
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(-1) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val drawerItems = listOf(
        DrawerItem(
            title = "Account",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = Screen.ProfileScreen.route
        ),
        DrawerItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = Screen.ProfileScreen.route
        ),
        DrawerItem(
            title = "Privacy Policy",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = Screen.ProfileScreen.route
        )
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))
                drawerItems.forEachIndexed { index, drawerItem ->
                    NavigationDrawerItem(
                        label = { Text(drawerItem.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            navController.navigate(drawerItem.route)

                            scope.launch {

                                drawerState.close()

                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) drawerItem.selectedIcon else drawerItem.unselectedIcon,
                                contentDescription = "${drawerItem.title} Button"
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "RoomLo",
                    onTrailingIconClicked = {
                        navController.navigate(Screen.ProfileScreen.route)
                    },
                    onLeadingIconClick = {
                        scope.launch {
                            drawerState.open()

                        }

                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.surface,
            bottomBar = { AppBottomBar() }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                AppSearchBar(viewModel.searchQuery)
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        RoomItemView()
                    }
                }
            }
        }
    }
}

data class DrawerItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
