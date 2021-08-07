/*
 * Last modified 8/7/21 3:13 PM
 */

package com.ihsan.sona3.ui.login

import android.app.Activity
import android.content.Context
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.entities.User
import com.truecaller.android.sdk.ITrueCallback

interface LoginContract {

    interface View {
        fun onSuccessTruCaller(user: User?)
        fun onFailTrueCaller(error: Throwable?)
    }

    interface Presenter {
        //fun saveUserLocale(user: User)
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
        fun getUserToken(username: String, password: String, activity: Activity?)
        fun saveToken(context: Context?, key: String?, token: String?)
        fun userLoginTrueCaller(payload: JsonObject, token: String?)
    }

    interface InterActor {
        fun initTrueCaller(activity: Activity, callback: ITrueCallback)
    }

}