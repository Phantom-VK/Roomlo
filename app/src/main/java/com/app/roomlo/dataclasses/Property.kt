package com.app.roomlo.dataclasses

data class Property(
    val owner: String = "",
    val propertyName: String = "",
    val ownerId:String = "",
    val rent: String = "",
    val sharingType: String = "",
    val size: String = "",
    val address:String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    var propertyImages: List<String> = emptyList()
)