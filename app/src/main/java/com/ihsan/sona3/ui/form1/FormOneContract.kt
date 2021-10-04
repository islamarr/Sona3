/*
 * Last modified 10/4/21, 6:49 PM
 */

package com.ihsan.sona3.ui.form1

import com.ihsan.sona3.data.model.form1.Governs

/**
 * Created by Ahmed Shehatah 4/10/2021
 * ahmed2002.eg@gmail.com
 */
interface FormOneContract {
    interface View {
        fun onStarted()
        fun onRetrievedGoverns(governs: List<Governs>)
        fun onRetrievedCenters(centers: List<Governs>)
        fun onFailure(message: String)
    }

    interface Presenter {
        fun getGoverns(token: String)
        fun getCenters(token: String, id: Int)
    }
}