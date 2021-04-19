package com.ihsan.sona3.ui.login

import android.app.Activity
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.network.Firebase
import com.ihsan.sona3.utils.Coroutines
import java.lang.Exception
import java.util.concurrent.TimeUnit

class LoginPresenter(var db: AppDatabase, private val mLoginView: LoginContract.View) :
    LoginContract.Presenter, LoginContract.OnLoginListener {

    private val mLoginInteractor: LoginInteractor = LoginInteractor(this)

    override fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun saveUserLocale(user: User) {
        Coroutines.io {
            db.getUserDao().upsert(user)
        }
    }

    override fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        mLoginInteractor.validateCredentials(activity, credential)
    }

    override fun onSuccess() {
        mLoginView.onLoginSuccess()
    }

    override fun onFailure(exception: Exception) {
        mLoginView.onLoginFailure(exception)
    }

}