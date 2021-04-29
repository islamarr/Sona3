package com.ihsan.sona3.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.utils.Coroutines
import com.truecaller.android.sdk.ITrueCallback

class LoginPresenter(var db: AppDatabase) : LoginContract.Presenter{

    private val mLoginInteractor: LoginInteractor = LoginInteractor()

    override fun saveUserLocale(user: User) {
        Coroutines.io {
            db.getUserDao().upsert(user)
        }
    }


    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        mLoginInteractor.initTrueCaller(activity, callback)
    }

}