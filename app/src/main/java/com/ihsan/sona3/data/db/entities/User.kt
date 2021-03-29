package com.ihsan.sona3.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity(tableName = "user")
class User(
    val name: String? = null,
    val email: String? = null,
    val imageUrl: String? = null,
    val gender: String? = null,
    val countryCode: String? = null,
    val phoneNumber: String? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val role: String? = null,
    val nationalId: String? = null,

    ) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}