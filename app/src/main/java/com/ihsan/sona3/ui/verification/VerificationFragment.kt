package com.ihsan.sona3.ui.verification

import android.os.Bundle
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
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.databinding.FragmentVerificationBinding
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.show
import com.ihsan.sona3.utils.toast


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

    override fun onLoginSuccess(user: User?) {

        //insert UserData into Room
        verificationPresenter.saveUserLocale(user!!)

        //Saving user token
        verificationPresenter.saveUserToken(
            requireActivity(),
            SharedKeyEnum.TOKEN.toString(),
            user.token
        )

        if (user.user_role == null) {
            //Navigate to Role
            val bundle = Bundle()
            bundle.putSerializable("userData", user)
            navController.navigate(
                R.id.action_verificationFragment_to_rolesFragment,
                bundle //User data
            )
        } else navController.navigate(R.id.action_verificationFragment_to_nav_home)

        requireActivity().toast("تم التسجيل بنجاح")

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

        //Get the phone number and assign it to the textView
        ("+20" + arguments?.getString("phoneNumber")).also {
            binding.tvPhoneNumber.text = it
        }

        db = AppDatabase.invoke(requireActivity())
        verificationPresenter = VerificationPresenter(db, this) { addDisposed() }

    }

}