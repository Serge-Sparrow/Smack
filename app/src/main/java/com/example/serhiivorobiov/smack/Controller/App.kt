package com.example.serhiivorobiov.smack.Controller

import android.app.Application
import com.example.serhiivorobiov.smack.Utilities.SharedPrefs

class App:Application() {


    companion object {
        lateinit var prefs:SharedPrefs
    }

    override fun onCreate() {
        prefs = SharedPrefs(applicationContext)
        super.onCreate()
    }
}