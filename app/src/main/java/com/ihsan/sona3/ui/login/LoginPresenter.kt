package com.ihsan.sona3.ui.login

import android.app.Activity
import android.content.Context
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.RoomHandler
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import com.truecaller.android.sdk.ITrueCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

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

    override fun userLoginTrueCaller(payload: JsonObject) {
        ApiSettings.apiInstance.userLoginTrueCaller(payload)
            .subscribeOn(Schedulers.io())
            .map { RoomHandler(db).convertToUserRoom(it) } //Convert response to Room Object
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> loginView.onSuccessTruCaller(response) }, //onSuccess
                { error -> loginView.onFailTrueCaller(error) } //onError
            )
    }

    override fun saveToken(context: Context?, key: String?, token: String?) {
        //SharedPreferencesUtil(context!!).saveTokenPreferences(token)
        Sona3Preferences().setString(key, token)
        Sona3Preferences().setBoolean(SharedKeyEnum.FIRST_LOGIN.toString(), false)
    }
}