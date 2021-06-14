package com.ihsan.sona3.ui.phoneNumber

import android.app.Activity.RESULT_OK
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.credentials.Credential
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.databinding.FragmentEnterPhoneNumberBinding
import com.ihsan.sona3.ui.login.LoginContract
import com.ihsan.sona3.ui.login.LoginPresenter
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class PhoneNumberFragment : BaseFragment<FragmentEnterPhoneNumberBinding>(),
    View.OnClickListener,
    PhoneNumberContract.View {

    private lateinit var navController: NavController
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var db: AppDatabase
    private lateinit var phoneNumberPresenter: PhoneNumberPresenter

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendCode -> login()
        }
    }

    private fun login() {
        showProgressDialog(requireContext())
        if (binding.ccp.isValidFullNumber) {
            phoneNumberPresenter.sendVerificationCode(
                binding.ccp.fullNumberWithPlus,
                requireActivity()
            )

        } else {
            requireActivity().toast("ادخل رقم هاتف صحيح")
            hideProgressDialog()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)
            // set the received data to the text view
            credential?.apply {
                var phoneNumber = credential.id
                phoneNumber = phoneNumber.replace("+20", "")
                binding.etPhoneNumber.setText(phoneNumber)
            }

        }
    }

    override fun onStartIntentSenderForResult(intent: PendingIntent) {
        try {
            startIntentSenderForResult(
                intent.intentSender,
                1, null, 0, 0, 0, Bundle()
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        storedVerificationId = verificationId
        resendToken = token
        val bundle = bundleOf("storedVerificationId" to storedVerificationId)
        navController.navigate(
            R.id.action_enterPhoneNumberFragment_to_verificationFragment,
            bundle
        )
        hideProgressDialog()
    }

    override fun onFailure(exception: FirebaseException) {
        hideProgressDialog()
        Timber.i("Exception on Code $exception")
        requireContext().toast("فشل في ارسال الرمز")
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentEnterPhoneNumberBinding
        get() = FragmentEnterPhoneNumberBinding::inflate

    override fun setupOnViewCreated(view: View) {
        binding.ccp.registerCarrierNumberEditText(binding.etPhoneNumber)
        navController = Navigation.findNavController(view)
        binding.btnSendCode.setOnClickListener(this)

        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )

        db = AppDatabase.invoke(requireActivity())
        phoneNumberPresenter = PhoneNumberPresenter(this)


        // to pop up phone number dialog
        phoneNumberPresenter.selectPhoneNumber(requireContext())
    }
}