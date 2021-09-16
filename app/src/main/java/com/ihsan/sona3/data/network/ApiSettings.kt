/*
 * Last modified 9/16/21 3:16 PM
 */

/*
 * Last modified 8/7/21 3:13 PM
 */

/*
 * Last modified 6/13/21 3:34 PM
 */

package com.ihsan.sona3.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by (Ameen Essa) on 6/13/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */

object ApiSettings {

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        Retrofit.Builder()
            .baseUrl("https://sona3-backend.herokuapp.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }


    val apiInstance: SonaApi by lazy {
        retrofit.create(SonaApi::class.java)
    }

}