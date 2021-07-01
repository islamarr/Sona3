/*
 * Last modified 7/1/21 5:06 PM
 */

package com.ihsan.sona3.ui.profile

import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.convertToUserRoom
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 7/1/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
class ProfilePresenter(
    val db: AppDatabase,
    private val profileView: ProfileContract.View
) : ProfileContract.Presenter {

    override fun getUserDataLocal() {
        db.getUserDao().getuser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> profileView.onDataLoaded(response) },
                { error -> profileView.onError(error.message!!) }
            )
    }

    override fun getUserDataRemote(token: String?) {
        ApiSettings.apiInstance.getUserData(token!!)
            .subscribeOn(Schedulers.io())
            .map {
                Timber.i("Data: $it")
                convertToUserRoom(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    Timber.i("Data: ${response.username}")
                    profileView.onDataLoaded(response)
                },
                { error -> profileView.onError(error.message!!) }
            )
    }
}