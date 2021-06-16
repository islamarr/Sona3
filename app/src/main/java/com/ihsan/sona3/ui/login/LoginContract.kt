package com.ihsan.sona3.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import android.service.voice.AlwaysOnHotwordDetector
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.model.UserResponse
import com.truecaller.android.sdk.ITrueCallback

interface LoginContract {

    interface View {
        fun onSuccessTruCaller(user: User?)
        fun onFailTrueCaller(error: Throwable?)
    }

    interface Presenter {
        //fun saveUserLocale(user: User)
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
        fun getUserToken(username: String, password: String, activity: Activity?)
        fun saveToken(activity: Activity?, token: String?)
        fun userLoginTrueCaller(payload: JsonObject, token: String?)
    }

    interface InterActor {
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
    }

}