package com.ihsan.sona3.ui.phoneNumber

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneNumberInteractor internal constructor(
    private val mOnLoginListener: PhoneNumberContract.OnLoginListener,
    private val mOnVerificationListener: PhoneNumberContract.OnVerificationListener
) : PhoneNumberContract.Interactor {

    override fun verifyPhoneNumber(activity: Activity?, phoneNumber: String) {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                mOnVerificationListener.onFailure(e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                mOnVerificationListener.onCodeSent(verificationId, token)
            }
        }

        val options = PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity!!)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun selectPhoneNumber(context: Context) {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val options = CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build()

        val credentialsClient = Credentials.getClient(context, options)
        val pendingIntent = credentialsClient.getHintPickerIntent(hintRequest)
        mOnLoginListener.setIntent(pendingIntent)
    }
}