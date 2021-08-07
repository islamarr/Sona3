/*
 * Last modified 8/6/21 5:19 PM
 */

package com.ihsan.sona3.data.model


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: Int
)