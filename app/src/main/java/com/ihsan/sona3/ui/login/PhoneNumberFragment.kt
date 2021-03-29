package com.ihsan.sona3.ui.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.FragmentEnterPhoneNumberBinding
import com.ihsan.sona3.utils.hide
import com.ihsan.sona3.utils.toast


class PhoneNumberFragment : Fragment(), View.OnClickListener {


    private lateinit var binding: FragmentEnterPhoneNumberBinding
    private lateinit var navController: NavController

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterPhoneNumberBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        setCallback()
        binding.ccp.registerCarrierNumberEditText(binding.etPhoneNumber)
        phoneNumberSelector()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnSendCode.setOnClickListener(this)

        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSendCode -> login()
        }
    }

    private fun login() {

        if (validateNumber()) {
            LoginPresenter().sendVerificationCode(
                binding.ccp.fullNumberWithPlus,
                requireActivity(), callbacks
            )
        } else requireActivity().toast("ادخل رقم هاتف صحيح")
    }


    private fun setCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.hide()
                requireContext().toast("فشل في ارسال الرمز")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resendToken = token
                val bundle = bundleOf("storedVerificationId" to storedVerificationId)
                navController.navigate(
                    R.id.action_enterPhoneNumberFragment_to_verificationFragment,
                    bundle
                )


            }
        }

    }

    private fun validateNumber(): Boolean {
        var isValidate = false
        binding.ccp.setPhoneNumberValidityChangeListener { isValidNumber ->
            if (isValidNumber) {
                isValidate = true
            }
        }
        return isValidate
    }

    private fun phoneNumberSelector() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val options = CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build()

        val credentialsClient = Credentials.getClient(requireContext(), options)
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(
                intent.intentSender,
                1, null, 0, 0, 0, Bundle()
            )
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val credential: Credential? = data?.getParcelableExtra(Credential.EXTRA_KEY)

            // set the received data t the text view
            credential?.apply {
                var phoneNumber = credential.id
                phoneNumber = phoneNumber.replace("+20", "")
                binding.etPhoneNumber.setText(phoneNumber)
            }

        }
    }


}