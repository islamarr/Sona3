/*
 * Last modified 6/15/21 4:26 PM
 */

package com.ihsan.sona3.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.ihsan.sona3.R
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 6/15/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */

enum class UserRoleEnum {
    Editor,
    Researcher,
    Verifier,
    Reviewer
}

fun saveTokenPreferences(activity: Activity?, token: String?) {

    val preference = activity!!.getSharedPreferences(
        activity.getString(R.string.shared_preference_name),
        Context.MODE_PRIVATE
    )

    val tokenPreference = getTokenPreferences(preference)

    //Check if we saved this token before
    if (tokenPreference != null && tokenPreference != token) {
        Timber.i("Saving new token...")

        with(preference.edit()) {
            putString("token", token)
            apply()
        }
    }
}

fun getTokenPreferences(sharedPreferences: SharedPreferences?): String? =
    sharedPreferences!!.getString("token", null)
