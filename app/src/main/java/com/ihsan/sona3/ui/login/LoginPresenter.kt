package com.ihsan.sona3.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.utils.Coroutines
import com.truecaller.android.sdk.ITrueCallback

class LoginPresenter(var db: AppDatabase, private val mLoginView: LoginContract.View) :
    LoginContract.Presenter, LoginContract.OnLoginListener, LoginContract.OnVerificationListener {

    private val mLoginInterActor: LoginInterActor = LoginInterActor(this, this)

    override fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity
    ) {
        mLoginInterActor.verifyPhoneNumber(activity, phoneNumber)
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

    override fun setIntent(intent: PendingIntent) {
        mLoginView.onStartIntentSenderForResult(intent)
    }

    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        mLoginInterActor.initTrueCaller(activity, callback)
    }

    override fun selectPhoneNumber(context: Context) {
        mLoginInterActor.selectPhoneNumber(context)
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        mLoginView.onCodeSent(verificationId, token)
    }

    override fun onFailure(exception: FirebaseException) {
        mLoginView.onFailure(exception)
    }
}