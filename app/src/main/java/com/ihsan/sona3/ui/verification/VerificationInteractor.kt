package com.ihsan.sona3.ui.verification

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.data.network.ApiSettings
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class VerificationInteractor internal constructor(private val mOnLoginListener: VerificationContract.OnLoginListener) :
    VerificationContract.InterActor {

    override fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getCurrentUserPayload()
                    //mOnLoginListener.onSuccess()
                } else {
                    mOnLoginListener.onFailure(task.exception!!)
                }
            }
    }

    override fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        performFirebaseLogin(activity, credential)
    }

    override fun getCurrentUserPayload() {
        var payload: String?

        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                payload = task.result!!.token.toString()

                Timber.d("PayLoad: $payload")

                checkUser(
                    payload = payload!!,
                    token = "Token db4e439c5269c2c311f186eb35b12c2ec1ef163d"
                )

            }
        }
    }

    override fun checkUser(payload: String, token: String) {

        var userLogin: UserResponse? = null

        ApiSettings.apiInstance.userLoginFirebase(payload = payload, token = token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> userLogin = response }, //OnSuccess
                { error -> Timber.e("Error: ${error.message}") }, //OnError
                { Timber.d("Response: $userLogin") } //OnComplete [End]
            )
    }
}