package com.ihsan.sona3.ui.login

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.ui.login.LoginContract.OnLoginListener
import java.lang.Exception
import java.util.concurrent.TimeUnit

class LoginInteractor internal constructor(
    private val mOnLoginListener: OnLoginListener
) : LoginContract.Interactor{

    override fun verifyPhonenumber(
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
}