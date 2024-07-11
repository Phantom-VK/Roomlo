package com.example.roomlo.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val USER_UID = ""
    }

    var userId: String?
        get() = sharedPreferences.getString(USER_UID, null)
        set(value) {
            sharedPreferences.edit().putString(USER_UID, value).apply()
        }
}
