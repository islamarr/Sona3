package com.ihsan.sona3.ui.login

import com.ihsan.sona3.data.db.entities.User

interface LoginContract {

    interface View {
        fun showMessage(message: String)
    }

    interface Presenter {
        fun saveUserLocale(user: User)
    }

}