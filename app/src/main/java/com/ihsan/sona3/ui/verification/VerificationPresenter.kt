package com.ihsan.sona3.ui.verification

import android.app.Activity
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.utils.Coroutines

class VerificationPresenter(
    var db: AppDatabase,
    private val verificationView: VerificationContract.View
) :
    VerificationContract.Presenter, VerificationContract.OnLoginListener {

    private val mVerificationInteractor: VerificationInteractor = VerificationInteractor(this)

    override fun saveUserLocale(user: User) {
        Coroutines.io {
            db.getUserDao().upsert(user)
        }
    }

    override fun checkCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        mVerificationInteractor.validateCredentials(activity, credential)
    }

    override fun onSuccess() {
        verificationView.onLoginSuccess()
    }

    override fun onFailure(exception: Exception) {
        verificationView.onLoginFailure(exception)
    }
}