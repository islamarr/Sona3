package com.ihsan.sona3.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.JsonObject
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.databinding.SplashFragmentBinding
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import com.truecaller.android.sdk.ITrueCallback
import com.truecaller.android.sdk.TrueError
import com.truecaller.android.sdk.TrueProfile
import com.truecaller.android.sdk.TruecallerSDK
import timber.log.Timber
import java.util.*


class LoginFragment : BaseFragment<SplashFragmentBinding>(),
    View.OnClickListener, ITrueCallback, LoginContract.View {


    private lateinit var navController: NavController
    private lateinit var db: AppDatabase
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var sharedPreferencesUtil: Sona3Preferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkCurrentUser()

        (activity as MainActivity).setHomeItemsVisibility(
            View.INVISIBLE,
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        )

        db = AppDatabase.invoke(requireActivity())
        loginPresenter = LoginPresenter(db, this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {

                loginPresenter.initTrueCaller(requireActivity(), this)
                loginPresenter.getUserToken("ashraf", "ashraf", activity)

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
            TruecallerSDK.getInstance()
                .onActivityResultObtained(requireActivity(), requestCode, resultCode, data)
        }
    }

    override fun onSuccessProfileShared(trueProfile: TrueProfile) {

//        val userTrueCaller = UserResponse()
//        userTrueCaller.apply {
//            username = trueProfile.phoneNumber
//            first_name = trueProfile.firstName
//            last_name = trueProfile.lastName
//            email = trueProfile.email
//            address = trueProfile.city
//            image = trueProfile.avatarUrl
//            token = trueProfile.accessToken
//        }
//        Timber.d("Verified user $userTrueCaller")
        Timber.d(
            "Payload: ${trueProfile.payload}" +
                    "Signature: ${trueProfile.signature}" +
                    "Algorithm: ${trueProfile.signatureAlgorithm}"
        )
        //Timber.d("Verified Successfully : $tcName  ${user.email}  ${user.phoneNumber}  ")
        /**
         * integrate with backend
         * TrueCaller API Service
         **/
        val trueCallerBodyObject = JsonObject()
        trueCallerBodyObject.addProperty("payload", trueProfile.payload)
        trueCallerBodyObject.addProperty("signature", trueProfile.signature)
        trueCallerBodyObject.addProperty("algorithm", trueProfile.signatureAlgorithm)

        //loginPresenter.saveUserLocale(user)
        loginPresenter.userLoginTrueCaller(
            trueCallerBodyObject,
            sharedPreferencesUtil.getString(SharedKeyEnum.TOKEN.toString())
        )
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


    /**
     * if the user login before.
     * check if token is stored && move to home.
     */
    private fun checkCurrentUser() {

        showProgressDialog(requireContext())

        val isFirstLogin =
            sharedPreferencesUtil.getBoolean(SharedKeyEnum.FIRST_LOGIN.toString(), true)
        val userToken = sharedPreferencesUtil.getString(SharedKeyEnum.TOKEN.toString())

        if (!isFirstLogin && userToken != null) {
            hideProgressDialog()
            navController.navigate(R.id.action_splashFragment_to_nav_home)
        }

        hideProgressDialog()
    }

    override fun onSuccessTruCaller(user: User?) {
        Timber.d("OnSuccessAPI Call: $user")
        Log.i("TrueCaller", "onSuccessTruCaller: ${user?.image}")

        //insert userData into Room DB for later use
        //loginPresenter.saveUserLocale(user!!)

        //navController.navigate(R.id.action_splashFragment_to_nav_home)
        val bundle = Bundle()
        bundle.putSerializable("userData", user!!)
        navController.navigate(
            R.id.action_splashFragment_to_rolesFragment,
            bundle //UserData to be updated
        )
    }

    override fun onFailTrueCaller(error: Throwable?) {
        Timber.e(error)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SplashFragmentBinding
        get() = SplashFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        binding.btnLogin.setOnClickListener(this)
        binding.tvSkip.setOnClickListener(this)

        sharedPreferencesUtil = Sona3Preferences()
    }

}