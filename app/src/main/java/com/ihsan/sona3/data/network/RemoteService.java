package com.ihsan.sona3.data.network;

import com.ihsan.sona3.data.db.entities.User;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Ali DOUIRI on 18/04/2018.
 * my.alidouiri@gmail.com
 */

public interface RemoteService {

    @GET(".")
    Flowable<Response<User>> getArticleFroimApi();
}
