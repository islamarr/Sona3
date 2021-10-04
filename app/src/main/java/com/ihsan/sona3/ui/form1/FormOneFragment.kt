/*
 * Last modified 10/4/21, 2:07 PM
 */

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.form1.Governs
import com.ihsan.sona3.databinding.FragmentFormOneBinding
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class FormOneFragment : BaseFragment<FragmentFormOneBinding>(), AdapterView.OnItemSelectedListener,
    FormOneContract.View {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormOneBinding
        get() = FragmentFormOneBinding::inflate
    private lateinit var presenter: FormOnePresenter
    private lateinit var navController: NavController
    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        presenter = FormOnePresenter(this)
        val activity = activity as MainActivity
        activity.fab.visibility = View.GONE
        binding.ivFamilyImage.setOnClickListener {
            imagePicker()
        }
        binding.citySpinner.onItemSelectedListener = this
        val token = "Token 96114cc99741e76b92fe9cdd2539dd2bf4c1ebc5"
        presenter.getGoverns(token)

        binding.btnNext.setOnClickListener { navController.navigate(R.id.action_formOneFragment_to_formThreeFragment) }

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
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
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


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, postition: Int, id: Long) {


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onStarted() {
        showProgressDialog(requireContext())
    }

    override fun onRetrievedGoverns(governs: List<Governs>) {
        hideProgressDialog()
        val cities = ArrayList<String>()
        for (city in governs) cities.add(city.name)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cities)
        binding.citySpinner.adapter = adapter
    }

    override fun onRetrievedCenters(centers: List<Governs>) {
        hideProgressDialog()
        val center = ArrayList<String>()
        for (c in centers) center.add(c.name)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, center)
        binding.citySpinner.adapter = adapter
    }

    override fun onFailure(message: String) {
        hideProgressDialog()
        requireActivity().toast(message)
    }


}