/*
 * Last modified 8/7/21 9:12 PM
 */

/*
 * Last modified 8/6/21 7:29 PM
 */

package com.ihsan.sona3.ui.home

import com.ihsan.sona3.utils.rx.AutoDispose
import com.ihsan.sona3.utils.rx.MyDisposable
import com.ihsan.sona3.utils.rx.MySchedulers

class HomePresenter(
    private val view: HomeContract.MainView,
    private val pagingRepository: PagingRepository,
    private val mySchedulers: MySchedulers,
    myDisposable: MyDisposable
) : AutoDispose by myDisposable {


    fun getFamilyData() = pagingRepository.getFamilyData()
        .subscribeOn(mySchedulers.computation())
        .observeOn(mySchedulers.ui())
        .subscribe({

            view.successGetFamilyData(it)
        }, {}).disposeWith(view)


}

