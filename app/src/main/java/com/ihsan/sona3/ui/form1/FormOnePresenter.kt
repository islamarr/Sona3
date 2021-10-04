/*
 * Last modified 10/4/21, 6:49 PM
 */

package com.ihsan.sona3.ui.form1

import com.ihsan.sona3.data.network.ApiSettings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by Ahmed Shehatah 4/10/2021
 * ahmed2002.eg@gmail.com
 */
class FormOnePresenter(private val view: FormOneContract.View) : FormOneContract.Presenter {
    override fun getGoverns(token: String) {
        view.onStarted()
        ApiSettings.apiInstance.governs(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                view.onRetrievedGoverns(it)
            }, {
                view.onFailure(it.message!!)
            })
    }

    override fun getCenters(token: String, id: Int) {
        view.onStarted()
        ApiSettings.apiInstance.centers(token,id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.onRetrievedCenters(it)
            },{
                view.onFailure(it.message!!)
            })
    }
}