package com.example.roomlo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.roomlo.screens.Navigation
import com.example.roomlo.ui.theme.RoomLoTheme
import com.example.roomlo.viewmodels.AuthViewModel
import com.example.roomlo.viewmodels.DatabaseViewModel
import com.example.roomlo.viewmodels.RoomViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RoomLoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(roomViewModel = RoomViewModel(), authViewModel = AuthViewModel(), navController = rememberNavController(),
                        dbViewModel = DatabaseViewModel()
                    )
                }
            }
        }
    }
}

