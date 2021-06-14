package com.ihsan.sona3.ui.login

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthProvider
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.Coroutines
import com.truecaller.android.sdk.ITrueCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LoginPresenter(var db: AppDatabase) : LoginContract.Presenter {

    private val mLoginInteractor: LoginInteractor = LoginInteractor()

    override fun saveUserLocale(user: User) {
        Coroutines.io {
            db.getUserDao().upsert(user)
        }
    }


    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        mLoginInteractor.initTrueCaller(activity, callback)
    }

    override fun getUserToken(username: String, password: String, preferences: SharedPreferences) {

        val jsonBodyObject = JsonObject()
        jsonBodyObject.addProperty("username", username)
        jsonBodyObject.addProperty("password", password)

        ApiSettings.apiInstance.userLoginUsername(jsonBodyObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> response.token?.let { saveToken(it, preferences) } },
                { error -> Timber.e(error) },
                { Timber.i("Completed") }
            )
    }

    override fun saveToken(token: String, preferences: SharedPreferences) {

        val tokenPreference = preferences.getString("token", null)

        //Check if we saved this token before
        if (tokenPreference != null && tokenPreference != token) {
            Timber.i("Saving new token...")
            with(preferences.edit()) {
                putString("token", token)
                apply()
            }
        }

        Timber.i("Token in preferences: $tokenPreference")
    }

}