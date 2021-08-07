/*
 * Last modified 8/7/21 1:05 PM
 */

package com.ihsan.sona3.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.observable


/**
 * Created by Ahmed Shehatah
 * ~~~~~~~ This Code Is For Testing ~~~~~~~
 */
class PagingRepository(private val dataPagingDataSource: DataPagingDataSource) {


    fun getFamilyData() = Pager(
        config = PagingConfig(20, 4, true, 20, 30),
        initialKey = 1,
        pagingSourceFactory = { dataPagingDataSource }
    ).observable


}