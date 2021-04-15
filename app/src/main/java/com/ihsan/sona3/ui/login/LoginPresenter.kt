package com.ihsan.sona3.ui.login

import android.app.Activity
import androidx.navigation.NavController
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.network.Firebase
import com.ihsan.sona3.utils.Coroutines

class LoginPresenter(var db: AppDatabase) : LoginContract.Presenter{

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        Firebase().login(phoneNumber, activity, callbacks)
    }

    fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        navController: NavController, activity: Activity,
        successNavId: Int,
        failedNavId: Int
    ) {
        Firebase().signInWithPhoneAuthCredential(
            credential,
            navController,
            activity,
            successNavId,
            failedNavId
        )
    }

    override fun saveUserLocale(user: User) {
        Coroutines.io {
            db.getUserDao().upsert(user)
        }
    }

}