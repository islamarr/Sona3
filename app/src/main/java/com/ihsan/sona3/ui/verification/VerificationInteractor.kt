package com.ihsan.sona3.ui.verification

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential

class VerificationInteractor internal constructor(private val mOnLoginListener: VerificationContract.OnLoginListener) : VerificationContract.InterActor {

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

}