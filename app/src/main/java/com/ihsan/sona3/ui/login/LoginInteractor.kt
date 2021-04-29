package com.ihsan.sona3.ui.login

import android.app.Activity
import androidx.core.content.ContextCompat
import com.ihsan.sona3.R
import com.truecaller.android.sdk.ITrueCallback
import com.truecaller.android.sdk.TruecallerSDK
import com.truecaller.android.sdk.TruecallerSdkScope

class LoginInteractor : LoginContract.InterActor {

    override fun initTrueCaller(activity: Activity, callback: ITrueCallback) {
        val trueScope = TruecallerSdkScope.Builder(activity, callback)
            .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
            .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
            .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_GET_STARTED)
            .footerType(TruecallerSdkScope.FOOTER_TYPE_CONTINUE)
            .buttonColor(ContextCompat.getColor(activity, R.color.purple_700))
            .buttonTextColor(ContextCompat.getColor(activity, R.color.white))
            .build()
        TruecallerSDK.init(trueScope)

    }

}
