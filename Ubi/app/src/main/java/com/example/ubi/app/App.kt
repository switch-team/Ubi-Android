package com.example.ubi.app

import android.app.Application
import android.util.Log

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("App", "created")
        tokenPrefs = TokenSharedPreferences(applicationContext)
    }
    companion object{
        lateinit var tokenPrefs : TokenSharedPreferences
    }
}