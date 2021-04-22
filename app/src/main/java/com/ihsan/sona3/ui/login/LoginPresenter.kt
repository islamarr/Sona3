package com.ihsan.sona3.ui.login

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.utils.Coroutines
import com.truecaller.android.sdk.ITrueCallback
import java.util.concurrent.TimeUnit

class LoginPresenter(var db: AppDatabase, private val mLoginView: LoginContract.View) :
    LoginContract.Presenter, LoginContract.OnLoginListener {

    private val mLoginInterActor: LoginInterActor = LoginInterActor(this)

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
        mLoginInterActor.validateCredentials(activity, credential)
    }

    override fun onSuccess() {
        mLoginView.onLoginSuccess()
    }

    override fun onFailure(exception: Exception) {
        mLoginView.onLoginFailure(exception)
    }

    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        mLoginInterActor.initTrueCaller(activity, callback)
    }

}