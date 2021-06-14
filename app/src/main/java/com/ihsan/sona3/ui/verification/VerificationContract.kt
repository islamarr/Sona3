package com.ihsan.sona3.ui.verification

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.db.entities.User
import com.truecaller.android.sdk.ITrueCallback

interface VerificationContract {

    interface View {
        fun onLoginSuccess()
        fun onLoginFailure(exception: Exception)
    }

    interface Presenter {
        fun saveUserLocale(user: User)
        fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential)
        //fun checkUserApi(payload: String, token: String)
    }

    interface InterActor {
        fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential)
        fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential)
        fun checkUser(payload: String, token: String)
        fun getCurrentUserPayload()
    }

    interface OnLoginListener {
        fun onSuccess()
        fun onFailure(exception: Exception)
    }
}