package com.app.roomlo.dataclasses



data class Property(
    val owner: String = "",
    var propertyName: String = "",
    val ownerId:String = "",
    val ownerMobNo:String = "",
    val ownerEmail:String = "",
    var rent: String = "",
    var sharingType: String = "",
    var size: String = "",
    var address:String = "",
    var city:String = "",
    var locality:String = "",
    var landmark:String = "",
    var floor:String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    var housetype: String = "",
    var roomtype: String = "",
    var availablefor: String = "",
    var deposit: String = "",
    var maintenance: String = "",
    var electricbill: String = "",
    var parking: String = "",
    var nonveg: String = "",
    var balcony: String = "",
    var bathroom: Int = 0,
    var toilet: Int = 0,
    var wifi: String = "",
    var amenities: List<String> = emptyList(),
    var propertyImages: List<String> = emptyList()
)