package com.ihsan.sona3.ui.login

import android.app.Activity
import android.content.Context
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.SharedPreferencesUtil
import com.ihsan.sona3.utils.convertToUserRoom
import com.truecaller.android.sdk.ITrueCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class LoginPresenter(
    var db: AppDatabase,
    private val loginView: LoginContract.View
) : LoginContract.Presenter {

    private val mLoginInteractor: LoginInteractor = LoginInteractor()

//    override fun saveUserLocale(user: User) {
//        saveUserLocal(db, user) //Util function
//    }


    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        mLoginInteractor.initTrueCaller(activity, callback)
    }

    override fun userLoginTrueCaller(payload: JsonObject, token: String?) {
        ApiSettings.apiInstance.userLoginTrueCaller(payload, token!!)
            .subscribeOn(Schedulers.io())
            .map { convertToUserRoom(it) } //Convert response to Room Object
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> loginView.onSuccessTruCaller(response) }, //onSuccess
                { error -> loginView.onFailTrueCaller(error) } //onError
            )
    }

    override fun getUserToken(username: String, password: String, activity: Activity?) {

        val jsonBodyObject = JsonObject()
        jsonBodyObject.addProperty("username", username)
        jsonBodyObject.addProperty("password", password)

        ApiSettings.apiInstance.userLoginUsername(jsonBodyObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> response.token?.let { saveToken(activity, it) } }, //onSuccess
                { error -> Timber.e(error) } //onError
            )
    }

    override fun saveToken(context: Context?, token: String?) {
        SharedPreferencesUtil(context!!).saveTokenPreferences(token)
    }
}