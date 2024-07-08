package com.example.roomlo.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class DatabaseViewModel : ViewModel() {

    private val _databaseRef = FirebaseDatabase.getInstance().getReference("Users")
    val database = _databaseRef



}