/*
 * Last modified 6/13/21 3:36 PM
 */

package com.ihsan.sona3.data.network

import com.google.gson.JsonObject
import com.ihsan.sona3.data.model.FamiliesDataList
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.data.model.form1.Governs
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
    ): Single<UserResponse>

    @POST("/en/api/v1/login/truecaller/")
    fun userLoginTrueCaller(
        @Body trueCallerBody: JsonObject,
    ): Single<UserResponse>

    @POST("/en/api/v1/login/username/")
    fun userLoginUsername(@Body loginBody: JsonObject): Single<UserResponse>

    @GET("/en/api/v1/me/")
    fun getUserData(@Header("Authorization") token: String): Single<UserResponse>

    @PATCH("/en/api/v1/me/")
    fun updateUserData(
        @Header("Authorization") token: String,
        @Body updatedUserData: JsonObject
    ): Completable

    @POST("/en/api/v1/logout/")
    fun logOut(@Header("Authorization") token: String): Completable

    @GET("/en/api/v1/governorates/")
    fun governs(@Header("Authorization") token: String): Single<List<Governs>>

    @GET("/en/api/v1/cities/{governorate_id}/")
    fun centers(
        @Header("Authorization") token: String,
        @Path("governorate_id") id: Int
    ): Single<List<Governs>>


    /**
     *
     * fake calls for testing
     * created by Ahmed shehatah
     */

    @GET("/posts")
    fun getPosts(): Single<List<FamiliesDataList>>

}