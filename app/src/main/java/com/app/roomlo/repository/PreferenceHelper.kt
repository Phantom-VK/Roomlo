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

}
