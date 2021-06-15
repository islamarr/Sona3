package com.ihsan.sona3.ui.verification

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.model.UserResponse

interface VerificationContract {

    interface View {
        fun onLoginSuccess(user: UserResponse)
        fun onLoginFailure(exception: Exception)
    }

    interface Presenter {
        fun saveUserLocale(user: User)
        fun saveUserToken(activity: Activity?, token: String?)
        fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential)
    }

    interface InterActor {
        fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential)
        fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential)
        fun checkUser(payload: String?, token: String?)
        fun getCurrentUserPayload(activity: Activity?)
    }

    interface OnLoginListener {
        fun onSuccess(user: UserResponse)
        fun onFailure(exception: Exception)
    }
}