/*
 * Last modified 8/6/21 7:42 PM
 */

/*
 * Last modified 8/6/21 7:22 PM
 */

package com.ihsan.sona3.ui.home

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import com.ihsan.sona3.data.model.FamilyDataTest
import com.ihsan.sona3.data.model.FamilyResult

/**
 *  ~~~~~~~~~~~ This Code For Testing  ~~~~~~~~~~~~~~~
 *   ****** Created By Ahmed Shehatah *********
 *              *_*
 */
interface HomeContract {
    interface MainView : LifecycleOwner {


        fun successGetFamilyData(pagingData: PagingData<FamilyDataTest>)

        //if you won't want use to paging
        fun successGetFamilyData(result: FamilyResult) {}
        fun errorGetProducts(throwable: Throwable) {}


    }
}