package com.ihsan.sona3.ui.roles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.RolesFragmentBinding
import com.ihsan.sona3.utils.show
import net.igenius.customcheckbox.CustomCheckBox
import timber.log.Timber

class RolesFragment : BaseFragment<RolesFragmentBinding>(), View.OnClickListener {

    private lateinit var viewModel: RolesViewModel
    private var checkboxGroup = ArrayList<CustomCheckBox>()

    override fun onClick(v: View?) {
        when (v!!.id) {

            //Selected role
            R.id.ckEditor -> validateCheckBox(0)
            R.id.ckResearcher -> validateCheckBox(1)
            R.id.ckReviewer -> validateCheckBox(2)
            R.id.ckVerifier -> validateCheckBox(3)

            //Show role
            R.id.ivEditor -> setBottomSheetDialog(R.id.ivEditor)
            R.id.ivResearcher -> setBottomSheetDialog(R.id.ivResearcher)
            R.id.ivVerifier -> setBottomSheetDialog(R.id.ivVerifier)
            R.id.ivReviewer -> setBottomSheetDialog(R.id.ivReviewer)

            //Next Button
            R.id.btnNext -> nextButtonPressed()
        }
    }

    private fun nextButtonPressed() {
        Timber.i("NextButton Pressed")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RolesViewModel::class.java)
    }

    private fun validateCheckBox(position: Int) {

        //val checkboxGroup = ArrayList<CustomCheckBox>()
        checkboxGroup.add(0, binding.ckEditor)
        checkboxGroup.add(1, binding.ckResearcher)
        checkboxGroup.add(2, binding.ckReviewer)
        checkboxGroup.add(3, binding.ckVerifier)
        for (checkbox in checkboxGroup)
            if (checkboxGroup[position] == checkbox) {
                checkbox.setChecked(true, true)
            } else checkbox.setChecked(false, false)

    }

    private fun setBottomSheetDialog(id: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheet = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_roles_bottom_sheet, null, false)
        dialog.setContentView(bottomSheet)

        val ivShowReviewForms = bottomSheet.findViewById<ImageView>(R.id.ivShowReviewForms)
        val ivShowSearchForms = bottomSheet.findViewById<ImageView>(R.id.ivShowSearchForms)
        val ivShowVerifyForms = bottomSheet.findViewById<ImageView>(R.id.ivShowVerifyForms)
        when (id) {
            R.id.ivResearcher -> ivShowSearchForms.show()

            R.id.ivReviewer -> {
                ivShowReviewForms.show()
                ivShowSearchForms.show()
            }
            R.id.ivVerifier -> {
                ivShowVerifyForms.show()
                ivShowSearchForms.show()
                ivShowReviewForms.show()
            }
        }
        dialog.show()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RolesFragmentBinding
        get() = RolesFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        binding.ckEditor.setOnClickListener(this)
        binding.ckReviewer.setOnClickListener(this)
        binding.ckResearcher.setOnClickListener(this)
        binding.ckVerifier.setOnClickListener(this)
        binding.ivEditor.setOnClickListener(this)
        binding.ivResearcher.setOnClickListener(this)
        binding.ivVerifier.setOnClickListener(this)
        binding.ivReviewer.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

}