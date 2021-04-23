package com.ihsan.sona3.ui.login

import android.app.PendingIntent
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.databinding.FragmentVerificationBinding
import com.ihsan.sona3.utils.show
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class VerificationFragment : Fragment(R.layout.fragment_verification), View.OnClickListener,
    LoginContract.View {
    private lateinit var binding: FragmentVerificationBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var db: AppDatabase
    private lateinit var loginPresenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentVerificationBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.btnContinue.setOnClickListener(this)
        binding.tvUnsentCode.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )

        db = AppDatabase.invoke(requireActivity())
        loginPresenter = LoginPresenter(db, this)

    }

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
            loginPresenter.checkCredentials(requireActivity(), credential)

        }

    }

    override fun onLoginSuccess() {
        navController.navigate(R.id.action_verificationFragment_to_nav_home)
        requireActivity().toast("تم التسجيل بنجاح")

        val user = auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            user.getIdToken(true).addOnCompleteListener { task2 ->
                if (task2.isSuccessful) {
                    val idToken = task2.result!!.token.toString()
                    // Send token to your backend via HTTPS
                    // ...
                    Timber.d(idToken)
                } else {
                    // Handle error -> task.getException();
                }


            }
        }
    }

    override fun onLoginFailure(exception: Exception) {
        if (exception is FirebaseAuthInvalidCredentialsException) {
            navController.navigate(R.id.action_verificationFragment_to_incorrectNumberFragment)
        }
    }

    override fun onStartIntentSenderForResult(intent: PendingIntent) {
        TODO("Not yet implemented")
    }

    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
        TODO("Not yet implemented")
    }

    override fun onFailure(exception: FirebaseException) {
        TODO("Not yet implemented")
    }
}