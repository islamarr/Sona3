/*
 * Last modified 7/12/21, 2:22 PM
 */

package com.ihsan.sona3.ui.main

import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import com.ihsan.sona3.utils.getToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 7/12/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
class MainPresenter(
    var db: AppDatabase,
    private val mainView: MainContract.View
) : MainContract.Presenter {

    override fun userLogOut() {
        ApiSettings.apiInstance.logOut(
            getToken()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mainView.onLogOutSuccess()
                    Timber.i("Logout, Completed")
                },
                { error ->
                    Timber.i(error)
                    mainView.onError("حدث خطا الرجاء المحاوله مره اخري")
                }
            )
    }

    override fun userDeleteLocal() {
        Sona3Preferences().removeValue(SharedKeyEnum.TOKEN.toString())
        Sona3Preferences().setBoolean(SharedKeyEnum.FIRST_LOGIN.toString(), true)

        db.getUserDao().delete()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Timber.i("Data Deleted") },
                { Timber.i("Error: $it") }
            )
    }
}