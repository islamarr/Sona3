package com.ihsan.sona3.ui.verification

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.db.entities.User

interface VerificationContract {

    interface View {
        fun onLoginSuccess(user: User?)
        fun onLoginFailure(exception: Exception)
    }

    interface Presenter {
        fun saveUserLocale(user: User)
        fun saveUserToken(context: Context?, key: String?, token: String?)
        fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential)
    }

    interface InterActor {
        fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential)
        fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential)
        fun checkUser(payload: String?)
        fun getCurrentUserPayload(context: Context?)
    }

    interface OnLoginListener {
        fun onSuccess(user: User?)
        fun onFailure(exception: Exception)
    }
}