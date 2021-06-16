/*
 * Last modified 6/15/21 4:26 PM
 */

package com.ihsan.sona3.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.ui.roles.RolesPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 6/15/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */

enum class UserRoleEnum {
    Editor,
    Researcher,
    Verifier,
    Reviewer
}

fun saveTokenPreferences(activity: Activity?, token: String?) {

    val preference = activity!!.getSharedPreferences(
        activity.getString(R.string.shared_preference_name),
        Context.MODE_PRIVATE
    )

    val tokenPreference = getTokenPreferences(preference)

    //Check if we saved this token before
    if (tokenPreference != null && tokenPreference != token) {
        Timber.i("Saving new token...")

        with(preference.edit()) {
            putString("token", token)
            apply()
        }
    }
}

fun getTokenPreferences(sharedPreferences: SharedPreferences?): String? =
    sharedPreferences!!.getString("token", null)


fun convertToUserRoom(user: UserResponse): User {
    val userToSave = User()
    userToSave.apply {
        address = user.address
        first_name = user.first_name
        last_name = user.last_name
        email = user.email
        image = user.image
        token = user.token
        national_id = user.national_id
        last_login = user.last_login
        role_approval_status = user.role_approval_status
    }
    return userToSave
}

fun saveUserLocal(db: AppDatabase, user: User, roles: RolesPresenter) {
    db.getUserDao().upsert(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable?) {}

            override fun onComplete() {
                Timber.d("User saved locally!!")
                roles.onSuccess()
            }

            override fun onError(e: Throwable?) {
                Timber.d("Error save user locally ${e!!.message}")
                roles.onError(e.message!!)
            }

        })
}