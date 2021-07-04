package com.ihsan.sona3

import android.app.Application
import android.content.Context
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var appContext: Context
    }
}