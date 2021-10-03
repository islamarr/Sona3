/*
 * Last modified 9/25/21 12:58 PM
 */

package com.ihsan.sona3.ui.form1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.databinding.FragmentFormOneBinding
import com.ihsan.sona3.ui.main.MainActivity
import timber.log.Timber

class FormOneFragment : BaseFragment<FragmentFormOneBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormOneBinding
        get() = FragmentFormOneBinding::inflate


    override fun setupOnViewCreated(view: View) {
        val activity = activity as MainActivity
        activity.fab.visibility = View.GONE
        binding.ivFamilyImage.setOnClickListener {
            imagePicker()
        }

    }

    private fun imagePicker() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                setImage(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setImage(uriResult: Uri) {
        Timber.i("$uriResult")
        Glide.with(this)
            .load(uriResult)
            .centerCrop()
            .override(100, 100)
            .into(binding.ivFamilyImage)
    }


}