/*
 * Last modified 8/4/21, 7:43 PM
 */

package com.ihsan.sona3.data.db

import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.ui.roles.RolesPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 8/4/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
class RoomHandler(db: AppDatabase) {

    private var handlerDb: AppDatabase? = null

    init {
        handlerDb = db
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


    fun saveUserLocal(user: User, rolesPresenterCallBack: RolesPresenter?) {
        handlerDb!!.getUserDao().upsert(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {}

                override fun onComplete() {
                    Timber.d("User saved locally!!")
                    rolesPresenterCallBack?.onSuccess()
                }

                override fun onError(e: Throwable?) {
                    Timber.d("Error save user locally ${e!!.message}")
                    rolesPresenterCallBack?.onError(e.message!!)
                }

            })
    }
}