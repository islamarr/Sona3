package com.ihsan.sona3.ui.phoneNumber

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthProvider

class PhoneNumberPresenter(private val mPhoneNumberView: PhoneNumberContract.View) :
    PhoneNumberContract.Presenter, PhoneNumberContract.OnLoginListener,
    PhoneNumberContract.OnVerificationListener {

    private val mPhoneNumberInteractor: PhoneNumberInteractor = PhoneNumberInteractor(this, this)

    override fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        mPhoneNumberInteractor.verifyPhoneNumber(activity, phoneNumber)
    }

    override fun selectPhoneNumber(context: Context) {
        mPhoneNumberInteractor.selectPhoneNumber(context)
    }

    override fun setIntent(intent: PendingIntent) {
        mPhoneNumberView.onStartIntentSenderForResult(intent)
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        mPhoneNumberView.onCodeSent(verificationId, token)
    }

    override fun onFailure(exception: FirebaseException) {
        mPhoneNumberView.onFailure(exception)
    }
}