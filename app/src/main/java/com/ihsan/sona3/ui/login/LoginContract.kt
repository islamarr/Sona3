package com.ihsan.sona3.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.entities.User
import com.truecaller.android.sdk.ITrueCallback

interface LoginContract {

    interface Presenter {
        fun saveUserLocale(user: User)
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
        fun getUserToken(username: String, password: String, preferences: SharedPreferences)
        fun saveToken(token: String, preferences: SharedPreferences)
    }

    interface InterActor {
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
    }

}