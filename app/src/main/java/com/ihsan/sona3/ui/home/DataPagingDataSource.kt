/*
 * Last modified 8/7/21 1:05 PM
 */

/*
 * Last modified 8/6/21 5:04 PM
 */

package com.ihsan.sona3.ui.home


import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.ihsan.sona3.data.model.FamilyDataTest
import com.ihsan.sona3.data.model.FamilyResult
import com.ihsan.sona3.data.network.SonaApi
import io.reactivex.Single


/**
 * Created by Ahmed Shehatah
 * ~~~~~~ This Code is For Testing~~~~~~~
 */
class DataPagingDataSource(private val remote: SonaApi) :
    RxPagingSource<Int, FamilyDataTest>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, FamilyDataTest>> {
        val page = params.key ?: 1
        return remote.familyData(page).subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .map { loadData(it, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun loadData(
        familyResult: FamilyResult,
        page: Int
    ): LoadResult<Int, FamilyDataTest> {
        return LoadResult.Page(
            data = familyResult.data,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (page == familyResult.meta.pagination.page) null else page + 1

        )
    }


    override fun getRefreshKey(state: PagingState<Int, FamilyDataTest>): Int? {
        TODO("Not yet implemented")
    }

}