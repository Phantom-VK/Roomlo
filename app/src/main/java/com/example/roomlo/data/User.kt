package com.example.roomlo.data

data class User(
    var name: String = "",
    var address: String = "",
    var email: String = "",
    var mobilenumber: String = "",
    var wpnumber: String = "",
    var isOwner: Boolean = false,
    var password: String = "",
    var profileImageUrl: String = "",
    var uid:String = ""
)
