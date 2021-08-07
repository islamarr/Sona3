/*
 * Last modified 8/7/21 9:16 PM
 */

package com.ihsan.sona3.ui.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.databinding.FragmentProfileBinding
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import com.ihsan.sona3.utils.UserRoleEnum
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class ProfileFragment : BaseFragment<FragmentProfileBinding>(),
    ProfileContract.View, View.OnClickListener {

    private lateinit var db: AppDatabase
    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var userData: User

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

        val token = "Token ${Sona3Preferences().getString(SharedKeyEnum.TOKEN.toString())}"
        Timber.i("Token: $token")

        //profilePresenter.getUserDataRemote(token)

        binding.editBtn.setOnClickListener(this)
        binding.ivProfilePhoto.setOnClickListener(this)
    }

    override fun onDataLoaded(user: User?) {

        Timber.i("User: ${user?.username}")
        hideProgressDialog()

        userData = user!!
        setData(user)
    }

    override fun onError(msg: String) {
        hideProgressDialog()

        requireContext().toast(msg)
        Timber.i("Error: $msg")
    }

    override fun onDataSavedLocal() {
        requireContext().toast("تم الحفظ")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.editBtn -> makeEditTextEnable()
            R.id.ivProfilePhoto -> profilePresenter.selectPhoto(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private var isEditable = false
    private fun makeEditTextEnable() {

        isEditable = !isEditable

        Timber.i("Edit: $isEditable")

        if (isEditable) {
            binding.editBtn.text = "حفظ"

            binding.apply {
                tvAddress.isEnabled = true
                tvEmail.isEnabled = true
                tvID.isEnabled = true
                tvName.isEnabled = true
            }

        } else {

            //Save User New Data
            profilePresenter.saveUpdatedUserLocal(getUserData())

            binding.editBtn.text = "تعديل"

            binding.apply {
                tvAddress.isEnabled = false
                tvEmail.isEnabled = false
                tvID.isEnabled = false
                tvName.isEnabled = false
            }
        }
    }

    private fun setData(user: User?) {

        binding.apply {
            tvName.setText(user?.first_name)
            tvAddress.setText(user?.address)
            tvEmail.setText(user?.email)
            tvPhoneNumber.setText(user?.username)
            tvID.setText(user?.national_id)
        }

        if (user?.image != null)
            setImage(Uri.parse(user.image))

        //Convert Role to Arabic Text..
        when (user?.user_role) {
            UserRoleEnum.Editor.toString() -> binding.tvType.setText(
                requireContext().getText(R.string.editor_or_volunteer)
            )
            UserRoleEnum.Reviewer.toString() -> binding.tvType.setText(
                requireContext().getText(R.string.reviewer)
            )
            UserRoleEnum.Researcher.toString() -> binding.tvType.setText(
                requireContext().getText(R.string.researcher)
            )
            UserRoleEnum.Verifier.toString() -> binding.tvType.setText(
                requireContext().getText(R.string.verifier)
            )
        }
    }

    private fun getUserData(): User =
        User(
            username = binding.tvPhoneNumber.text.toString(),
            email = binding.tvEmail.text.toString(),
            first_name = binding.tvName.text.toString(),
            address = binding.tvAddress.text.toString(),
            national_id = binding.tvID.text.toString()
        )


    override fun openGallery() {
        val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, 2000)
        }
    }

    private fun setImage(uriResult: Uri) {
        Timber.i("$uriResult")
        Glide.with(this)
            .load(uriResult)
            .centerCrop()
            .override(100, 100)
            .into(binding.ivProfilePhoto)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Timber.i("$resultCode, $data")

        if (resultCode == RESULT_OK && requestCode == 2000) {
            val uriResult = data!!.data

            setImage(uriResult!!)

            userData.image = uriResult.toString()
            profilePresenter.saveUpdatedUserLocal(userData)
        } else {
            Timber.i("$resultCode:  $data")
        }
    }
}