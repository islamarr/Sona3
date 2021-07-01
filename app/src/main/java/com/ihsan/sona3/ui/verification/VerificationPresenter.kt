package com.ihsan.sona3.ui.verification

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.utils.SharedPreferencesUtil
import com.ihsan.sona3.utils.saveUserLocal
import io.reactivex.rxjava3.disposables.Disposable

class VerificationPresenter(
    var db: AppDatabase,
    private val verificationView: VerificationContract.View,
    private val disposableFunction: (disposable: Disposable?) -> Unit
) :
    VerificationContract.Presenter, VerificationContract.OnLoginListener {

    private val mVerificationInteractor: VerificationInteractor =
        VerificationInteractor(this, disposableFunction)

    override fun saveUserLocale(user: User) {
        saveUserLocal(db, user, null) //Util function
    }

    override fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        mVerificationInteractor.validateCredentials(activity, credential)
    }

    override fun saveUserToken(context: Context?, token: String?) {
        SharedPreferencesUtil(context!!).saveTokenPreferences(token!!)
    }

    override fun onSuccess(user: User?) {
        verificationView.onLoginSuccess(user)
    }

    override fun onFailure(exception: Exception) {
        verificationView.onLoginFailure(exception)
    }
}