package com.ihsan.sona3.ui.verification

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ihsan.sona3.R
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.convertToUserRoom
import com.ihsan.sona3.utils.getTokenPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class VerificationInteractor internal constructor(
    private val mOnLoginListener: VerificationContract.OnLoginListener,
    private val disposableFunction: (disposable: Disposable?) -> Unit
) :
    VerificationContract.InterActor {

    override fun performFirebaseLogin(activity: Activity?, credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getCurrentUserPayload(activity)
                } else {
                    mOnLoginListener.onFailure(task.exception!!)
                }
            }
    }

    override fun validateCredentials(activity: Activity?, credential: PhoneAuthCredential) {
        performFirebaseLogin(activity, credential)
    }

    override fun getCurrentUserPayload(activity: Activity?) {
        var payload: String?

        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                payload = task.result!!.token.toString()
                Timber.d("PayLoad: $payload")

                checkUser(
                    payload = payload!!,
                    token = getTokenPreferences(
                        activity!!.getSharedPreferences(
                            activity.getString(R.string.shared_preference_name),
                            Context.MODE_PRIVATE
                        )
                    )
                )

            }
        }
    }

    override fun checkUser(payload: String?, token: String?) {

        //var userLogin: UserResponse? = null
        val payloadJsonObject = JsonObject()

        payloadJsonObject.addProperty("payload", payload)
        Timber.i("Payload Json: $payloadJsonObject")

        disposableFunction(
            ApiSettings.apiInstance.userLoginFirebase(payload = payloadJsonObject, token = token!!)
                .subscribeOn(Schedulers.io())
                .map { convertToUserRoom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> mOnLoginListener.onSuccess(response) }, //OnSuccess
                    { error -> Timber.e("Error: ${error.message}") }, //OnError
                )
        )
    }
}