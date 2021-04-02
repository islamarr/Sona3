package com.ihsan.sona3.data.network

import android.app.Activity
import androidx.navigation.NavController
import com.google.firebase.auth.*
import com.ihsan.sona3.utils.toast
import java.util.concurrent.TimeUnit

class Firebase {
    private val auth = FirebaseAuth.getInstance()
    fun login(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.Builder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        navController: NavController, activity: Activity,
        successNavId: Int,
        failedNavId: Int
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate(successNavId)
                    activity.toast("تم التسجيل بنجاح")

                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        navController.navigate(failedNavId)
                    }
                }
            }
    }
}