package com.ihsan.sona3

import android.app.Application
import com.ihsan.sona3.utils.SharedPreferencesUtil
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        //Check user first login
        val shared = SharedPreferencesUtil(this)
        val firstLogin = shared.checkFirstLogin()
        val token = shared.getTokenPreferences()
        Timber.i("User first login: $firstLogin, Token: $token")
    }
}