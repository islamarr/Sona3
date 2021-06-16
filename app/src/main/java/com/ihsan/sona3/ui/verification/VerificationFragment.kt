package com.ihsan.sona3.ui.verification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.model.UserResponse
import com.ihsan.sona3.databinding.FragmentVerificationBinding
import com.ihsan.sona3.utils.show
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class VerificationFragment : BaseFragment<FragmentVerificationBinding>(), View.OnClickListener,
    VerificationContract.View {

    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var db: AppDatabase
    private lateinit var verificationPresenter: VerificationPresenter

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnContinue -> checkVerificationCode()
            R.id.tvUnsentCode -> navController.navigate(R.id.action_verificationFragment_to_incorrectNumberFragment)
        }
    }

    private fun checkVerificationCode() {
        binding.progressBar.show()
        val code = binding.otpView.otp
        if (code!!.isNotEmpty()) {
            val verificationCode = arguments?.getString("storedVerificationId")
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                verificationCode.toString(), code
            )
            verificationPresenter.checkCredentials(requireActivity(), credential)

        }

    }

    override fun onLoginSuccess(user: UserResponse) {

        navController.navigate(R.id.action_verificationFragment_to_rolesFragment)
        requireActivity().toast("تم التسجيل بنجاح")

        verificationPresenter.saveUserToken(requireActivity(), user.token)

    }

    override fun onLoginFailure(exception: Exception) {
        if (exception is FirebaseAuthInvalidCredentialsException) {
            navController.navigate(R.id.action_verificationFragment_to_incorrectNumberFragment)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVerificationBinding
        get() = FragmentVerificationBinding::inflate

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        binding.btnContinue.setOnClickListener(this)
        binding.tvUnsentCode.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )

        db = AppDatabase.invoke(requireActivity())
        verificationPresenter = VerificationPresenter(db, this) { addDisposed() }

    }

}