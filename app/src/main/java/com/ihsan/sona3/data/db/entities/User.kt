package com.ihsan.sona3.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

const val CURRENT_USER_ID = 0

@Entity(tableName = "user")
class User(
    var address: String? = null,
    var email: String? = null,
    var first_name: String? = null,
    var image: String? = null,
    var last_login: String? = null,
    var last_name: String? = null,
    var national_id: String? = null,
    var role_approval_status: String? = null,
    var token: String? = null,
    var user_role: String? = null,
    var username: String? = null

) : Serializable {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}