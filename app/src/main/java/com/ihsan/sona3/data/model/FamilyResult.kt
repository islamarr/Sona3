/*
 * Last modified 8/7/21 9:16 PM
 */

/*
 * Last modified 8/6/21 5:17 PM
 */

package com.ihsan.sona3.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class FamilyResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<FamilyDataTest>,
    @SerializedName("meta")
    val meta: Meta
) : Serializable