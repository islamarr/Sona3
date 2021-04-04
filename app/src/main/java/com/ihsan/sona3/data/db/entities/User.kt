package com.ihsan.sona3.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity(tableName = "user")
class User(
    var name: String? = null,
    var email: String? = null,
    var imageUrl: String? = null,
    var gender: String? = null,
    var countryCode: String? = null,
    var phoneNumber: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var role: String? = null,
    var nationalId: String? = null,
    var profileUrl: String? = null,

    ) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}