/*
 * Last modified 9/17/21 9:49 PM
 */

package com.ihsan.sona3.ui.home

import com.ihsan.sona3.data.network.ApiSettings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenter(var homeView: HomeContract.View) : HomeContract.Presenter {

    override fun getData() {
        val post = ApiSettings.fakeApiInstance.getPosts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                homeView.onSuccess(it)
            }, {
                homeView.onFailure(it.message.toString())
            })

    }
}