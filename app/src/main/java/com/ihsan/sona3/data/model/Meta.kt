/*
 * Last modified 8/6/21 5:19 PM
 */

package com.ihsan.sona3.data.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("pagination")
    val pagination: Pagination
)