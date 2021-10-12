package com.ihsan.sona3.ui.form1

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.form1.Governs
import com.ihsan.sona3.databinding.FragmentFormOneBinding
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.utils.Sona3Preferences
import com.ihsan.sona3.utils.getToken
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class FormOneFragment : BaseFragment<FragmentFormOneBinding>(), AdapterView.OnItemSelectedListener,
    FormOneContract.View {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormOneBinding
        get() = FragmentFormOneBinding::inflate
    private lateinit var presenter: FormOnePresenter
    private lateinit var navController: NavController
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var sona3Pref: Sona3Preferences

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        presenter = FormOnePresenter(this)
        val activity = activity as MainActivity
        activity.binding.appBar.fab.visibility = View.GONE
        binding.ivFamilyImage.setOnClickListener {
            imagePicker()
        }
        binding.citySpinner.onItemSelectedListener = this
        activityLauncher()
        binding.citySpinner.setSelection(Adapter.NO_SELECTION, true)
        binding.citySpinner.clearFocus()
        sona3Pref = Sona3Preferences()
        presenter.getGoverns(getToken())

        binding.btnNext.setOnClickListener { navController.navigate(R.id.action_formOneFragment_to_formThreeFragment) }

    }

    private fun imagePicker() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("أختر الصورة من").setPositiveButton("الاستوديو") { _, _ ->
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
        }.setNegativeButton("الكاميرا") { _, _ ->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResultLauncher.launch(intent)
        }
        dialog.show()

    }

    private fun activityLauncher() {
        activityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == RESULT_OK && it.data != null
                ) {
                    val bundle = it.data!!.extras
                    try { val bitmap = bundle!!.get("data") as Bitmap
                        setImage(bitmap, null)
                    } catch (ex: Exception) {

                        val uri = it.data!!.data
                        setImage(null, uri)
                    }


                }
            }
    }

    private fun setImage(bitmap: Bitmap?, uri: Uri?) {
        if (uri == null) {
            Timber.i("$bitmap")
            Glide.with(this)
                .load(bitmap)
                .centerCrop()
                .override(100, 100)
                .into(binding.ivFamilyImage)
        } else {
            Timber.i("$uri")
            Glide.with(this)
                .load(uri)
                .centerCrop()
                .override(100, 100)
                .into(binding.ivFamilyImage)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, postition: Int, id: Long) {
        presenter.getCenters(getToken(), postition + 1)
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
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cities)
        binding.citySpinner.adapter = adapter
    }

    override fun onRetrievedCenters(centers: List<Governs>) {
        hideProgressDialog()
        val center = ArrayList<String>()
        for (c in centers) center.add(c.name)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, center)
        binding.centerSpinner.adapter = adapter
    }

    override fun onFailure(message: String) {
        hideProgressDialog()
        requireActivity().toast(message)
    }


}