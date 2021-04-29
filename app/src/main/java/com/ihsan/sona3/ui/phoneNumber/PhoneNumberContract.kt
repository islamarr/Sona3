package com.ihsan.sona3.ui.phoneNumber

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthProvider

interface PhoneNumberContract {

    interface View {
        fun onStartIntentSenderForResult(intent: PendingIntent)
        fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken)
        fun onFailure(exception: FirebaseException)
    }

    interface Interactor {
        fun verifyPhoneNumber(activity: Activity?, phoneNumber: String)
        fun selectPhoneNumber(context: Context)
    }

    interface Presenter {
        fun sendVerificationCode(phoneNumber: String, activity: Activity)
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