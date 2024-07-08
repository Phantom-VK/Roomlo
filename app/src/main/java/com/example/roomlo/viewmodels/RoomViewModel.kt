package com.example.roomlo.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RoomViewModel : ViewModel() {


    private var _searchQuery by mutableStateOf("")
    var searchQuery = _searchQuery





}