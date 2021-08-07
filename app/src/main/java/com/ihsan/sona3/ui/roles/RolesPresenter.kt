/*
 * Last modified 6/16/21 11:17 PM
 */

package com.ihsan.sona3.ui.roles

import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.RoomHandler
import com.ihsan.sona3.data.db.entities.User

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

    override fun updateUserRole(user: User?, userRole: String?) {
        // TODO: 6/16/2021 Update User Role data from selected
        if (user != null && userRole != null) {
            user.user_role = userRole //Update the user role from selected
            RoomHandler(db).saveUserLocal(user, this) //Update user role
        }
    }

    override fun onSuccess() {
        rolesView.onSuccess()
    }

    override fun onError(message: String) {
        rolesView.onError(message)
    }
}