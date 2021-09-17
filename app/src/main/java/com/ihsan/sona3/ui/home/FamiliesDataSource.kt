/*
 * Last modified 9/17/21 8:42 PM
 */

package com.ihsan.sona3.ui.home

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.ihsan.sona3.data.model.FamiliesDataList
import com.ihsan.sona3.data.network.SonaApi
import io.reactivex.Single

class FamiliesDataSource(private val apiService: SonaApi) :
    RxPagingSource<Int, FamiliesDataList>() {
    override fun getRefreshKey(state: PagingState<Int, FamiliesDataList>): Int? {
        TODO("Not yet implemented")
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FamiliesDataList>> {
        TODO("Not yet implemented")
    }


}