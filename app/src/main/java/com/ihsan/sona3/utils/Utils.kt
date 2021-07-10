/*
 * Last modified 6/15/21 4:26 PM
 */

package com.ihsan.sona3.utils

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

enum class SharedKeyEnum {
    TOKEN,
    FIRST_LOGIN
}

fun convertToUserRoom(user: UserResponse): User =
    User(
        address = user.address,
        first_name = user.first_name,
        last_name = user.last_name,
        email = user.email,
        image = user.image,
        token = user.token,
        national_id = user.national_id,
        last_login = user.last_login,
        role_approval_status = user.role_approval_status,
        username = user.username,
        user_role = user.user_role
    )


fun saveUserLocal(db: AppDatabase, user: User, roles: RolesPresenter?) {
    db.getUserDao().upsert(user)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : CompletableObserver {
            override fun onSubscribe(d: Disposable?) {}

            override fun onComplete() {
                Timber.d("User saved locally!!")
                roles?.onSuccess()
            }

            override fun onError(e: Throwable?) {
                Timber.d("Error save user locally ${e!!.message}")
                roles?.onError(e.message!!)
            }

        })
}