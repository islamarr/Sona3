package com.ihsan.sona3.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.databinding.SplashFragmentBinding
import com.ihsan.sona3.utils.Coroutines
import com.truecaller.android.sdk.*
import timber.log.Timber
import java.util.*


class LoginFragment : Fragment(), View.OnClickListener, ITrueCallback{

    private lateinit var binding: SplashFragmentBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: LoginViewModel
    private lateinit var db:AppDatabase
    private lateinit var loginPresenter: LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnLogin.setOnClickListener(this)
        binding.tvSkip.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        checkCurrentUser()
        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )

        db =  AppDatabase.invoke(requireActivity())
      //  loginPresenter = LoginPresenter(db)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {

                initTrueCaller()

                val isTCLoginMethod = TruecallerSDK.getInstance().isUsable

                if (isTCLoginMethod) {

                    val locale = Locale("ar")
                    TruecallerSDK.getInstance().setLocale(locale)
                    TruecallerSDK.getInstance().getUserProfile(this)
                } else {
                    navController.navigate(R.id.action_splashFragment_to_enterPhoneNumberFragment)
                }

            }
            R.id.tvSkip -> navController.navigate(R.id.action_splashFragment_to_nav_home)
        }
    }

    private fun initTrueCaller() {
        val trueScope = TruecallerSdkScope.Builder(requireActivity(), this)
            .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
            .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
            .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_GET_STARTED)
            .footerType(TruecallerSdkScope.FOOTER_TYPE_CONTINUE)
            .buttonColor(ContextCompat.getColor(requireActivity(), R.color.purple_700))
            .buttonTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            .build()

        TruecallerSDK.init(trueScope)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
            TruecallerSDK.getInstance().onActivityResultObtained(requireActivity(), requestCode, resultCode, data)
        }
    }

    override fun onSuccessProfileShared(trueProfile: TrueProfile) {

        val user = User()
        val tcName = trueProfile.firstName + " " + trueProfile.lastName
        user.name = tcName
        user.email = trueProfile.email
        user.gender = trueProfile.gender
        user.countryCode = trueProfile.countryCode
        user.imageUrl = trueProfile.avatarUrl
        user.city = trueProfile.city
        user.profileUrl = trueProfile.url
        user.phoneNumber = trueProfile.phoneNumber

        Timber.d("Verified Successfully : $tcName  ${user.email}  ${user.phoneNumber}  ")
       //  integrate with backend

       // loginPresenter.saveUserLocale(user)

        navController.navigate(R.id.action_splashFragment_to_nav_home)

    }

    override fun onFailureProfileShared(trueError: TrueError) {
        Timber.d("onFailureProfileShared: ${trueError.errorType}")
        when (trueError.errorType) {
            TrueError.ERROR_TYPE_USER_DENIED -> {
            }
            TrueError.ERROR_TYPE_CONTINUE_WITH_DIFFERENT_NUMBER -> {
                navController.navigate(R.id.action_splashFragment_to_enterPhoneNumberFragment)
            }
            else -> {
                Toast.makeText(
                    requireActivity(),
                    "getString(R.string.try_again2)",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onVerificationRequired(p: TrueError?) {
        Timber.d("onVerificationRequired: $p ")
    }


    fun checkCurrentUser() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navController.navigate(R.id.action_splashFragment_to_nav_home)
        }
    }

}