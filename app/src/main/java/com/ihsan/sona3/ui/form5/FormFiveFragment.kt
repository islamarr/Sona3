/*
 * Last modified 10/9/21 4:18 PM
 */

package com.ihsan.sona3.ui.form5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.form2.Form2Data
import com.ihsan.sona3.data.model.form3.Form3Model
import com.ihsan.sona3.databinding.FragmentFormFiveBinding
import com.ihsan.sona3.databinding.FragmentFormFourBinding
import com.ihsan.sona3.databinding.FragmentFormThreeBinding
import com.ihsan.sona3.databinding.FragmentFormTwoBinding
import com.ihsan.sona3.ui.form3.FormThreeAdapter

class FormFiveFragment() : BaseFragment<FragmentFormFiveBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormFiveBinding
        get() = FragmentFormFiveBinding::inflate

    private lateinit var navController: NavController

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        binding.next.setOnClickListener {
            navController.navigate(R.id.action_formFiveFragment_to_formSevenFragment)

        }
        binding.befor.setOnClickListener {
            navController.navigate(R.id.action_formFiveFragment_to_formFourFragment)

        }
    }

    private fun getData() {
    }

}