package com.ihsan.sona3.ui.login

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.data.Firebase

class LoginPresenter {

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        Firebase().login(phoneNumber, activity, callbacks)
    }

}