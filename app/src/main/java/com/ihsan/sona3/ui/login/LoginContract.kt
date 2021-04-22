package com.ihsan.sona3.ui.login

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.entities.User
import com.truecaller.android.sdk.ITrueCallback

interface LoginContract {

    interface View {
        fun onLoginSuccess()
        fun onLoginFailure(exception: Exception)
    }

    interface Presenter {
        fun saveUserLocale(user: User)
        fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential)
        fun sendVerificationCode(
            phoneNumber: String,
            activity: Activity,
            callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
        )

        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
    }

    interface InterActor {
        fun verifyPhoneNumber(
            activity: Activity?,
            phoneNumber: String,
            callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
        )

        fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential)
        fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential)
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
    }

    interface OnLoginListener {
        fun onSuccess()
        fun onFailure(exception: Exception)
    }

    interface OnVerificationListener {
        fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)
        fun onFailure(exception: FirebaseException)
    }

}