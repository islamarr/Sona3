package com.ihsan.sona3.ui.verification

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.gson.JsonObject
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.RoomHandler
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class VerificationInteractor internal constructor(
    private val db: AppDatabase,
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

    override fun getCurrentUserPayload(context: Context?) {
        var payload: String?

        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                payload = task.result!!.token.toString()
                Timber.d("PayLoad: $payload")
                Timber.d("Token: ${Sona3Preferences().getString(SharedKeyEnum.TOKEN.toString())}")

                checkUser(payload = payload!!)

            }
        }
    }

    override fun checkUser(payload: String?) {

        //var userLogin: UserResponse? = null
        val payloadJsonObject = JsonObject()

        payloadJsonObject.addProperty("payload", payload)
        Timber.i("Payload Json: $payloadJsonObject")

        disposableFunction(
            ApiSettings.apiInstance.userLoginFirebase(payload = payloadJsonObject)
                .subscribeOn(Schedulers.io())
                .map { RoomHandler(db).convertToUserRoom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> mOnLoginListener.onSuccess(response) }, //OnSuccess
                    { error -> Timber.e("Error: ${error.message}") }, //OnError
                )
        )
    }
}