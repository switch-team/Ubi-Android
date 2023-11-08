package com.example.ubi.app

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class ImageSharedPreferences(context: Context) {
    private val prefsUri = "uri_prefs"
    private val keyUri = "imgUri"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsUri,0)

    var uri: String?
        get() = prefs.getString(keyUri, null)
        set(value) {
            val result = prefs.edit().putString(keyUri,value).commit()
            Log.i("App", "SharedPreference : $result")
        }

    fun removeImg(){
        prefs.edit().remove(keyUri).apply()
    }
}