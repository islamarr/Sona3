package com.ihsan.sona3.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.FragmentEnterPhoneNumberBinding
import com.ihsan.sona3.utils.hide
import com.ihsan.sona3.utils.show
import com.ihsan.sona3.utils.toast
import java.util.concurrent.TimeUnit


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

    fun login() {
        var mobileNumber = binding.etPhoneNumber.text.toString().trim()
        if (mobileNumber.isNotEmpty()) {
            mobileNumber = "+2$mobileNumber"
            sendVerificationCode(mobileNumber)
        } else requireActivity().toast("ادخل رقم هاتفك")
    }

    private fun sendVerificationCode(mobileNumber: String) {
        binding.progressBar.show()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobileNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun setCallback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.hide()
                requireContext().toast("فشل")
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


}