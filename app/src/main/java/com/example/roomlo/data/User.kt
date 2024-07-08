package com.example.roomlo.data

data class User(
    var name: String? = null,
    var address: String? = null,
    var email: String? = null,
    var mobilenumber: String? = null,
    var wpnumber: String? = null,
    var isOwner: Boolean ?= null,
    var password: String ?= null
)
