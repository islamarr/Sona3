/*
 * Last modified 6/15/21 5:13 PM
 */

package com.ihsan.sona3.ui.roles

import com.ihsan.sona3.data.db.entities.User

/**
 * Created by (Ameen Essa) on 6/15/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
interface RolesContract {

    interface View {
        fun onSuccess()
        fun onError(message: String)
    }

    interface Presenter {
        fun updateUserRole(user: User?, userRole: String?)
    }

    interface OnSaveListener {
        fun onSuccess()
        fun onError(message: String)
    }
}