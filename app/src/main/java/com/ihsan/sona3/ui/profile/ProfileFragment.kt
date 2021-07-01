package com.ihsan.sona3.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.MainActivity
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.data.network.SonaApi
import com.ihsan.sona3.databinding.FragmentProfileBinding
import com.ihsan.sona3.utils.SharedPreferencesUtil
import com.ihsan.sona3.utils.UserRoleEnum
import com.ihsan.sona3.utils.toast
import timber.log.Timber

class ProfileFragment : BaseFragment<FragmentProfileBinding>(), ProfileContract.View {

    private var user: User? = null

    private lateinit var db: AppDatabase
    private lateinit var profilePresenter: ProfilePresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).fab.visibility = View.GONE
        (activity as MainActivity).toolbar.title = getString(R.string.profile)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun setupOnViewCreated(view: View) {
        showProgressDialog(requireContext())

        db = AppDatabase.invoke(requireContext())

        profilePresenter = ProfilePresenter(db, this)
        profilePresenter.getUserDataLocal()

        val token = "Token ${SharedPreferencesUtil(requireContext()).getTokenPreferences()}"
        Timber.i("Token: $token")

        //profilePresenter.getUserDataRemote(token)
    }

    override fun onDataLoaded(user: User?) {

        Timber.i("User: ${user?.username}")
        hideProgressDialog()

        binding.apply {
            tvName.text = user?.first_name + user?.last_name
            tvAddress.text = user?.address
            tvEmail.text = user?.email
            tvPhoneNumber.text = user?.username
            tvID.text = user?.national_id
        }

        //Convert Role to Arabic Text..
        when (user?.user_role) {
            UserRoleEnum.Editor.toString() -> binding.tvType.text =
                requireContext().getText(R.string.editor_or_volunteer)
            UserRoleEnum.Reviewer.toString() -> binding.tvType.text =
                requireContext().getText(R.string.reviewer)
            UserRoleEnum.Researcher.toString() -> binding.tvType.text =
                requireContext().getText(R.string.researcher)
            UserRoleEnum.Verifier.toString() -> binding.tvType.text =
                requireContext().getText(R.string.verifier)
        }
    }

    override fun onError(msg: String) {
        hideProgressDialog()

        requireContext().toast(msg)
        Timber.i("Error: $msg")
    }
}