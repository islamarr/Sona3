/*
 * Last modified 8/7/21 3:56 PM
 */

/*
 * Last modified 6/13/21 3:36 PM
 */

package com.ihsan.sona3.data.network

import com.google.gson.JsonObject
import com.ihsan.sona3.data.model.FamilyResult
import com.ihsan.sona3.data.model.UserResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

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

    /**
    ~~~~~~~~~~ This code for Testing ~~~~~~~~~~~~~~
     ***** Created By Ahmed Shehatah *******
     *          *_*
     */

    @GET("/posts")
    fun familyData(@Query("page") page: Int): io.reactivex.Single<FamilyResult>


}