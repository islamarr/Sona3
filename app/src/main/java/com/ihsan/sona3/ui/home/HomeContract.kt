package com.ihsan.sona3.ui.home

import com.ihsan.sona3.data.model.FamiliesDataList

interface HomeContract {

    interface View {
        fun onSuccess(list: List<FamiliesDataList>)
        fun onFailure(msg: String)
    }

    interface Presenter {
        fun getData()
    }



}