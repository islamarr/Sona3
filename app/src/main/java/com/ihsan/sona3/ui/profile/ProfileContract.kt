/*
 * Last modified 7/1/21 5:04 PM
 */

package com.ihsan.sona3.ui.profile

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
        fun onDataSavedLocal()
    }

    interface Presenter {
        fun getUserDataLocal()
        fun getUserDataRemote(token: String?)
        fun saveUpdatedUserLocal(user: User?)
    }

}