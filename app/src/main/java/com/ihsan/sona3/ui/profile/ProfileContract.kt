/*
 * Last modified 7/1/21 5:04 PM
 */

package com.ihsan.sona3.ui.profile

import android.app.Activity
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.entities.User

/**
 * Created by (Ameen Essa) on 7/1/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
interface ProfileContract {

    interface View {
        fun onDataLoaded(user: User?)
        fun onError(msg: String)
        fun onDataSavedLocal(user: User?)
        fun onDataSavedRemote()
        fun openGallery()
        fun requestPermission(permission: String?)
    }

    interface Presenter {
        fun getUserDataLocal()
        fun getUserDataRemote(token: String?)
        fun saveUpdatedUserLocal(user: User?)
        fun saveUpdatedUserRemote(token: String?, user: JsonObject?)
        fun selectPhoto(activity: Activity?, permission: String?)
    }

}