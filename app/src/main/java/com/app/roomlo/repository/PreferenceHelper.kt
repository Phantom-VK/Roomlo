package com.app.roomlo.repository

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val USER_UID = "userId"
        private const val USER_NAME = "username"
        private const val USER_EMAIL = "email"
        private const val USER_MOBILE = "mobile"
        private const val PROFILE_IMG_URL = "profileImageUrl"
        private const val USER_ADDRESS = "address"
        private const val USER_WP_NUMBER = "wpNumber"
        private const val USER_TYPE = "userType"
    }

    var userId: String?
        get() = sharedPreferences.getString(USER_UID, null)
        set(value) {
            sharedPreferences.edit().putString(USER_UID, value).apply()
        }
    var username:String
        get() = sharedPreferences.getString(USER_NAME, null).toString()
        set(value) {
            sharedPreferences.edit().putString(USER_NAME, value).apply()
        }
    var useremail:String
        get() = sharedPreferences.getString(USER_EMAIL, null).toString()
        set(value) {
            sharedPreferences.edit().putString(USER_EMAIL, value).apply()
        }
    var mobilenumber:String
        get() = sharedPreferences.getString(USER_MOBILE, null).toString()
        set(value) {
            sharedPreferences.edit().putString(USER_MOBILE, value).apply()
        }

    var profileImageUrl:String
        get() = sharedPreferences.getString(PROFILE_IMG_URL, null).toString()
        set(value) {
            sharedPreferences.edit().putString(PROFILE_IMG_URL, value).apply()
        }
    var userAddress:String
        get() = sharedPreferences.getString(USER_ADDRESS, null).toString()
        set(value) {
            sharedPreferences.edit().putString(USER_ADDRESS, value).apply()
        }
    var wpnumber:String
        get() = sharedPreferences.getString(USER_WP_NUMBER, null).toString()
        set(value) {
            sharedPreferences.edit().putString(USER_WP_NUMBER, value).apply()
        }
    var userType:String
        get() = sharedPreferences.getString(USER_TYPE, null).toString()
        set(value) {
            sharedPreferences.edit().putString(USER_TYPE, value).apply()
        }
}
