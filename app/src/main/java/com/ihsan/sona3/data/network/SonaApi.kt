/*
 * Last modified 9/16/21 3:19 PM
 */

/*
 * Last modified 8/7/21 3:56 PM
 */

/*
 * Last modified 6/13/21 3:36 PM
 */

package com.ihsan.sona3.data.network

import com.google.gson.JsonObject
import com.ihsan.sona3.data.model.UserResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by (Ameen Essa) on 6/13/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
interface SonaApi {

    @POST("/en/api/v1/login/firebase/")
    fun userLoginFirebase(
        @Body payload: JsonObject,
        @Header("Authorization") token: String
    ): Single<UserResponse>

    @POST("/en/api/v1/login/truecaller/")
    fun userLoginTrueCaller(
        @Body trueCallerBody: JsonObject,
        @Header("Authorization") token: String
    ): Single<UserResponse>

    @POST("/en/api/v1/login/username/")
    fun userLoginUsername(@Body loginBody: JsonObject): Single<UserResponse>

    @GET("/en/api/v1/me/")
    fun getUserData(@Header("Authorization") token: String): Single<UserResponse>

    @POST("/en/api/v1/logout/")
    fun logOut(@Header("Authorization") token: String): Completable



}