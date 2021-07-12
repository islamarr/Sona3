/*
 * Last modified 7/1/21 5:06 PM
 */

package com.ihsan.sona3.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.convertToUserRoom
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.jar.Manifest

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

    override fun saveUpdatedUserLocal(user: User?) {
        db.getUserDao().upsert(user!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { profileView.onDataSavedLocal() },
                { error -> profileView.onError(error.message!!) }
            )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun selectPhoto(activity: Activity?, permission: String?) {
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                permission!!
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity.requestPermissions(arrayOf(permission), 2000)
        } else {

            profileView.openGallery()
        }
    }
}