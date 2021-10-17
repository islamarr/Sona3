/*
 * Last modified 10/9/21 4:18 PM
 */

package com.ihsan.sona3.ui.form7

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
import com.ihsan.sona3.databinding.FragmentFormSevenBinding
import com.ihsan.sona3.ui.form3.FormThreeAdapter

class FormSevenFragment() : BaseFragment<FragmentFormSevenBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormSevenBinding
        get() = FragmentFormSevenBinding::inflate

    private lateinit var navController: NavController

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        binding.befor.setOnClickListener {
            navController.navigate(R.id.action_formSevenFragment_to_formFiveFragment)

        }}

    private fun getData() {
    }

}