package com.ihsan.sona3.ui.login

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.firebase.FirebaseException
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

class LoginInteractor internal constructor(
    private val mOnLoginListener: OnLoginListener,
    private val mOnVerificationListener: LoginContract.OnVerificationListener
) : LoginContract.InterActor {
    override fun verifyPhoneNumber(
        activity: Activity?,
        phoneNumber: String
    ) {
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
