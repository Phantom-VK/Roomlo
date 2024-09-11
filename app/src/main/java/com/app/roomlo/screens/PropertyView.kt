package com.app.roomlo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.roomlo.navigation.Screen
import com.app.roomlo.repository.PreferenceHelper
import com.app.roomlo.screens.components.AppAlertDialog
import com.app.roomlo.screens.components.PropertyItemView
import com.app.roomlo.ui.theme.dimens
import com.app.roomlo.viewmodels.SharedViewModel


// Refactored PropertyView
@Composable
fun PropertyView(
    paddingValues: PaddingValues,
    navController: NavController,
    preferenceHelper: PreferenceHelper
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    // Fetch user properties when the composable is first composed
    sharedViewModel.fetchUserProperties()

    val propertiesList by sharedViewModel.userProperties.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.dimens.small2)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    if (preferenceHelper.username.isEmpty()) {
                        openAlertDialog.value = true
                    } else {
                        navController.navigate(Screen.ListPropertyScaffoldScreen.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(text = "List Property", color = MaterialTheme.colorScheme.primary)
            }
        }

        if (propertiesList?.propertyList.isNullOrEmpty()) {
            // Show a placeholder when the list is empty
            Text(
                text = "No properties found.",
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                propertiesList?.propertyList?.let { properties ->
                    items(properties) { property ->
                        PropertyItemView(property)
                    }
                }
            }
        }
    }

    if (openAlertDialog.value) {
        AppAlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
            },
            dialogTitle = "Alert",
            dialogText = "Please complete user profile first!",
            icon = Icons.Default.Info
        )
    }
}


