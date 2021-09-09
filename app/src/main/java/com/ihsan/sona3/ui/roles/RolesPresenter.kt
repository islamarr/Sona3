/*
 * Last modified 6/16/21 11:17 PM
 */

package com.ihsan.sona3.ui.roles

import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.RoomHandler
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by (Ameen Essa) on 6/16/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
class RolesPresenter(
    val db: AppDatabase,
    private val rolesView: RolesContract.View
) :
    RolesContract.Presenter, RolesContract.OnSaveListener {

    private lateinit var userData: User

    override fun updateUserRole(user: User?, userRole: String?) {
        // TODO: 6/16/2021 Update User Role data from selected
        if (user != null && userRole != null) {
            userData = user
            userData.user_role = userRole //Update the user role from selected
            RoomHandler(db).saveUserLocal(userData, this) //Update user role
        }
    }

    override fun onSuccess() {
        //rolesView.onSuccess()
        saveUserRemote(userData)
    }

    override fun onError(message: String) {
        rolesView.onError(message)
    }

    override fun saveUserRemote(user: User?) {

        val userObject = JsonObject()
        user?.also {
            userObject.addProperty("email", it.email)
            userObject.addProperty("first_name", it.first_name)
            userObject.addProperty("last_name", it.last_name)
            userObject.addProperty("last_login", it.last_login)
            userObject.addProperty("user_role", it.user_role)
            userObject.addProperty("address", it.address)
            userObject.addProperty("national_id", it.national_id)
            userObject.addProperty("image", it.image)
        }

        ApiSettings.apiInstance.updateUserData(
            "token ${Sona3Preferences().getString(SharedKeyEnum.TOKEN.toString())}",
            userObject
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { rolesView.onSuccess() },
                { error -> Timber.e(error.localizedMessage) }
            )
    }
}