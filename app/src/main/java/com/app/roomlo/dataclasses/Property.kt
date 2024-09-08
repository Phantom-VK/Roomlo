package com.app.roomlo.dataclasses

//data class Property(
//    val owner: String = "",
//    val propertyName: String = "",
//    val ownerId:String = "",
//    val rent: String = "",
//    val sharingType: String = "",
//    val size: String = "",
//    val address:String = "",
//    val createdAt: String = "",
//    val updatedAt: String = "",
//    var propertyImages: List<String> = emptyList()
//)

data class Property(
    val owner: String = "",
    val propertyName: String = "",
    val ownerId:String = "",
    val ownerMobNo:String = "",
    val ownerEmail:String = "",
    var rent: String = "",
    val sharingType: String = "",
    var size: String = "",
    var address:String = "",
    var city:String = "",
    var locality:String = "",
    var landmark:String = "",
    var floor:String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val housetype: String = "",
    val roomtype: String = "",
    val availablefor: String = "",
    var deposit: String = "",
    var maintenance: String = "",
    var electricbill: String = "",
    var parking: String = "",
    var nonveg: String = "",
    val balcony: String = "",
    val bathroom: Int = 0,
    val toilet: Int = 0,
    var wifi: String = "",
    var Amenities: List<String> = emptyList(),
    var propertyImages: List<String> = emptyList()
)