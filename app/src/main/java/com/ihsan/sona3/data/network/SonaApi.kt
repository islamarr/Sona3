/*
 * Last modified 6/13/21 3:36 PM
 */

package com.ihsan.sona3.data.network

import com.ihsan.sona3.data.model.UserResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.*

/**
 * Created by (Ameen Essa) on 6/13/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
interface SonaApi {

    @POST("/en/api/v1/login/firebase/")
    fun userLoginFirebase(
        @Body payload: String,
        @Header("Authorization") token: String
    ): Flowable<UserResponse>

    @GET("/en/api/v1/login/username/")
    fun userLoginUsername(username: String, password: String): Flowable<UserResponse>

}