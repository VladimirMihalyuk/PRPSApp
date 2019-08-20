package com.example.prpsapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}

class Prefs (context: Context) {
    val PREFS_FILENAME = " com.example.prpsapp.prefsField"
    val LOG_IN_EMAIL = "log_in_email"
    val prefsField: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var emailClient: String?
        get() = prefsField.getString(LOG_IN_EMAIL, null)
        set(value : String?) = prefsField.edit().putString(LOG_IN_EMAIL, value).apply()
}