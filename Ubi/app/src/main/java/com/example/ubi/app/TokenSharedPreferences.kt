package com.example.ubi.app

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri

class TokenSharedPreferences(context: Context) {
    private val prefsFilename = "token_prefs"
    private val keyAccessToken = "accessToken"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename,0)

    var accessToken: String?
        get() = prefs.getString(keyAccessToken,null)
        set(value) {
            val result = prefs.edit().putString(keyAccessToken,value).commit()
            Log.i("App", "SharedPreference : $result")
        }

    fun removeToken(){
        prefs.edit().remove(keyAccessToken).apply()
    }
}
