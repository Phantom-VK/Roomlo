package com.example.roomlo.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RoomViewModel : ViewModel() {

    private var _fullname by mutableStateOf("")
    var fullname = _fullname

    private var _password by mutableStateOf("")
    var password = _password


    private var _mobilenumber by mutableStateOf("")
    var mobilenumber = _mobilenumber

    private var _email by mutableStateOf("")
    var email = _email

    private var _useraddress by mutableStateOf("")
    var useraddress = _useraddress

    private var _searchQuery by mutableStateOf("")
    var searchQuery = _searchQuery





}