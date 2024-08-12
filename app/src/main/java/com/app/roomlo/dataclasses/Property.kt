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

data class Temp(
    val owner: String = "",
    val propertyName: String = "",
    val ownerId:String = "",
    val ownerMobNo:String = "",
    val ownerEmail:String = "",
    val rent: String = "",
    val sharingType: String = "",
    val size: String = "",
    val address:String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val housetype: String = "",
    val roomtype: String = "",
    val availablefor: String = "",
    val deposit: String = "",
    val maintenance: String = "",
    val electricbill: String = "",
    val parking: String = "",
    val nonveg: String = "",
    val balcony: String = "",
    val bathroom: Int = 0,
    val toilet: Int = 0,
    val wifi: String = "",
    val Amenities: List<String> = emptyList(),
    var propertyImages: List<String> = emptyList()
)