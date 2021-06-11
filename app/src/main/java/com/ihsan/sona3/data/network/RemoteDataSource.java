package com.ihsan.sona3.data.network;

import com.ihsan.sona3.data.db.entities.User;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Response;

/**
 * Created by Ali DOUIRI on 18/04/2018.
 * my.alidouiri@gmail.com
 */

public class RemoteDataSource {

    private static final String TAG = "RemoteDataSource";

    private RemoteService mRemoteService;

    public RemoteDataSource(RemoteService remoteService) {
        this.mRemoteService = remoteService;
    }

    /**
     * Get articles from api
     *
     * @return
     */
    public Flowable<Response<User>> getArticlesFromApi() {

        return mRemoteService.getArticleFroimApi();
    }
}
