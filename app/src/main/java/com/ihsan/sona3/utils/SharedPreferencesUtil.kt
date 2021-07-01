/*
 * Last modified 7/1/21 1:47 PM
 */

package com.ihsan.sona3.utils

import android.content.Context
import com.ihsan.sona3.R
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 7/1/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
class SharedPreferencesUtil(private val context: Context) {

    private val preference by lazy {
        context.getSharedPreferences(
            context.getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    fun saveTokenPreferences(token: String?) {

        val tokenPreference = getTokenPreferences()

        //Check if we saved this token before
        if (tokenPreference != token) {
            Timber.i("Saving new token...")

            with(preference.edit()) {
                putString("token", token)
                putBoolean("first", false)
                apply()
            }
        }
    }

    fun checkFirstLogin(): Boolean = getFirstLoginBoolean()

    fun getTokenPreferences(): String? =
        preference!!.getString("token", null)

    fun getFirstLoginBoolean(): Boolean =
        preference!!.getBoolean("first", true)

    fun deleteSharedPreferences() {
        preference!!.edit().clear().apply()
    }
}