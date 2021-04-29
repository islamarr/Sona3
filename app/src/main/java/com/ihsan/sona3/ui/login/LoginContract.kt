package com.ihsan.sona3.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.entities.User
import com.truecaller.android.sdk.ITrueCallback

interface LoginContract {

    interface View {
        fun onStartIntentSenderForResult(intent: PendingIntent)
        fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)
        fun onFailure(exception: FirebaseException)
    }

    interface Presenter {
        fun saveUserLocale(user: User)
        fun sendVerificationCode(
            phoneNumber: String,
            activity: Activity,
        )

        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
        fun selectPhoneNumber(context: Context)
    }

    interface InterActor {
        fun verifyPhoneNumber(
            activity: Activity?,
            phoneNumber: String,
        )

      //  fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential)
      //  fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential)
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
        fun selectPhoneNumber(context: Context)
    }

    interface OnLoginListener {
        fun setIntent(intent: PendingIntent)
    }

    interface OnVerificationListener {
        fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)
        fun onFailure(exception: FirebaseException)
    }


}