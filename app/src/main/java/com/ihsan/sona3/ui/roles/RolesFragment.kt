package com.ihsan.sona3.ui.roles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.RolesFragmentBinding
import com.ihsan.sona3.utils.show
import net.igenius.customcheckbox.CustomCheckBox

class RolesFragment : Fragment(R.layout.roles_fragment), View.OnClickListener {


    companion object {
        fun newInstance() = RolesFragment()
    }

    private lateinit var viewModel: RolesViewModel

    private lateinit var binding: RolesFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = RolesFragmentBinding.bind(view)
        binding.ckEditor.setOnClickListener(this)
        binding.ckReviewer.setOnClickListener(this)
        binding.ckResearcher.setOnClickListener(this)
        binding.ckVerifier.setOnClickListener(this)
        binding.ivEditor.setOnClickListener(this)
        binding.ivResearcher.setOnClickListener(this)
        binding.ivVerifier.setOnClickListener(this)
        binding.ivReviewer.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ckEditor -> validateCheckBox(0)
            R.id.ckResearcher -> validateCheckBox(1)
            R.id.ckReviewer -> validateCheckBox(2)
            R.id.ckVerifier -> validateCheckBox(3)
            R.id.ivEditor -> setBottomSheetDialog(R.id.ivEditor)
            R.id.ivResearcher -> setBottomSheetDialog(R.id.ivResearcher)
            R.id.ivVerifier -> setBottomSheetDialog(R.id.ivVerifier)
            R.id.ivReviewer -> setBottomSheetDialog(R.id.ivReviewer)

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RolesViewModel::class.java)


    }

    private fun validateCheckBox(position: Int) {

        val checkboxGroup = ArrayList<CustomCheckBox>()
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

}