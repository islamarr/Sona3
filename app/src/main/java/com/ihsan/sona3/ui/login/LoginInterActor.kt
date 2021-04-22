package com.ihsan.sona3.ui.login

import android.app.Activity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.R
import com.ihsan.sona3.ui.login.LoginContract.OnLoginListener
import com.truecaller.android.sdk.ITrueCallback
import com.truecaller.android.sdk.TruecallerSDK
import com.truecaller.android.sdk.TruecallerSdkScope
import java.util.concurrent.TimeUnit

class LoginInterActor internal constructor(
    private val mOnLoginListener: OnLoginListener
) : LoginContract.InterActor {

    override fun verifyPhoneNumber(
        activity: Activity?,
        phoneNumber: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity!!)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mOnLoginListener.onSuccess()
                } else {
                    mOnLoginListener.onFailure(task.exception!!)
                }
            }
    }


    override fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        /*if (email == "") {
            mOnLoginListener.onFailure(email)
            return
        }
        if (!email!!.contains("@")) {
            mOnLoginListener.onFailure(email)
            return
        }
        if (password == "") {
            mOnLoginListener.onFailure("Password is empty")
            return
        }
        if (password!!.length < 6) {
            mOnLoginListener.onFailure("Short password")
            return
        }*/
        performFirebaseLogin(activity, credential)
    }

    /* override fun onVerificationCompleted(p0: PhoneAuthCredential) {}

     override fun onVerificationFailed(exception: FirebaseException) {
          mOnVerificationListener.onFailure(exception)
      }

      override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
          mOnVerificationListener.onCodeSent(verificationId, token)
      }*/

    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        val trueScope = TruecallerSdkScope.Builder(activity, callback)
            .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
            .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
            .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_GET_STARTED)
            .footerType(TruecallerSdkScope.FOOTER_TYPE_CONTINUE)
            .buttonColor(ContextCompat.getColor(activity, R.color.purple_700))
            .buttonTextColor(ContextCompat.getColor(activity, R.color.white))
            .build()
        TruecallerSDK.init(trueScope)

    }
}
