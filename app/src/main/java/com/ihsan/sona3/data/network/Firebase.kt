package com.ihsan.sona3.data.network

import android.app.Activity
import androidx.navigation.NavController
import com.google.firebase.auth.*
import com.ihsan.sona3.utils.toast
import timber.log.Timber
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

                    val user = auth.currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val name = user.displayName
                        val email = user.email
                        val photoUrl = user.photoUrl

                        // Check if user's email is verified
                        val emailVerified = user.isEmailVerified

                        // The user's ID, unique to the Firebase project. Do NOT use this value to
                        // authenticate with your backend server, if you have one. Use
                        // FirebaseUser.getToken() instead.
                        val uid = user.uid

                        user.getIdToken(true).addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                val idToken = task2.result!!.token.toString()
                                // Send token to your backend via HTTPS
                                // ...
                                Timber.d(idToken)
                            } else {
                                // Handle error -> task.getException();
                            }


                        }
                    }
                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        navController.navigate(failedNavId)
                    }
                }
            }
    }
}