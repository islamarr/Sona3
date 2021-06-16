package com.ihsan.sona3.ui.verification

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.utils.Coroutines
import com.ihsan.sona3.utils.saveTokenPreferences
import com.ihsan.sona3.utils.saveUserLocal
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class VerificationPresenter(
    var db: AppDatabase,
    private val verificationView: VerificationContract.View,
    private val disposableFunction: (disposable: Disposable?) -> Unit
) :
    VerificationContract.Presenter, VerificationContract.OnLoginListener {

    private val mVerificationInteractor: VerificationInteractor =
        VerificationInteractor(this, disposableFunction)

//    override fun saveUserLocale(user: User) {
//        saveUserLocal(db, user) //Util function
//    }

    override fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        mVerificationInteractor.validateCredentials(activity, credential)
    }

    override fun saveUserToken(activity: Activity?, token: String?) {
        //Util function
        saveTokenPreferences(activity, token)
    }

    override fun onSuccess(user: User?) {
        verificationView.onLoginSuccess(user)
    }

    override fun onFailure(exception: Exception) {
        verificationView.onLoginFailure(exception)
    }
}