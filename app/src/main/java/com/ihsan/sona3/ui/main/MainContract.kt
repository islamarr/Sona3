/*
 * Last modified 7/12/21, 2:22 PM
 */

package com.ihsan.sona3.ui.main

/**
 * Created by (Ameen Essa) on 7/12/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
interface MainContract {

    interface View {
        fun onLogOutSuccess()
        fun onError(error: String?)
    }

    interface Presenter {
        fun userLogOut()
        fun userDeleteLocal()
    }
}