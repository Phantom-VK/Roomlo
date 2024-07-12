package com.example.roomlo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.roomlo.data.Permission
import com.example.roomlo.data.PreferenceHelper
import com.example.roomlo.data.UserRepository
import com.example.roomlo.screens.Navigation
import com.example.roomlo.ui.theme.RoomLoTheme
import com.example.roomlo.viewmodels.AuthViewModel
import com.example.roomlo.viewmodels.DatabaseViewModel
import com.example.roomlo.viewmodels.RoomViewModel
import com.example.roomlo.viewmodels.SharedViewModel
import com.example.roomlo.viewmodels.SharedViewModelFactory
import com.example.roomlo.viewmodels.UserProfileViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val databaseViewModel = DatabaseViewModel(context)
            val userRepository = UserRepository(context)
            val sharedViewModel: SharedViewModel by viewModels { SharedViewModelFactory(userRepository) }
            val preferenceHelper = PreferenceHelper(context)
            val authViewModel = AuthViewModel(context, databaseViewModel, sharedViewModel, preferenceHelper)
            val permission = Permission()
            val profileViewModel = UserProfileViewModel(userRepository)

            RoomLoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        roomViewModel = RoomViewModel(), authViewModel = authViewModel, navController = rememberNavController(),
                        dbViewModel = databaseViewModel, sharedViewModel = sharedViewModel, preferenceHelper = preferenceHelper,
                        permissions = permission, profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}

