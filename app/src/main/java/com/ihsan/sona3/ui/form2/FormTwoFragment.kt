/*
 * Last modified 10/9/21 4:18 PM
 */

package com.ihsan.sona3.ui.form2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.data.model.form2.Form2Data
import com.ihsan.sona3.data.model.form3.Form3Model
import com.ihsan.sona3.databinding.FragmentFormThreeBinding
import com.ihsan.sona3.databinding.FragmentFormTwoBinding
import com.ihsan.sona3.ui.form3.FormThreeAdapter

class FormTwoFragment() : BaseFragment<FragmentFormTwoBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormTwoBinding
        get() = FragmentFormTwoBinding::inflate

    private lateinit var navController: NavController
    private val itemList = ArrayList<Form2Data>()
    private lateinit var adapter: FormTwoAdapter

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        adapter = FormTwoAdapter(arrayListOf())
        binding.rVFrom2.layoutManager = LinearLayoutManager(requireContext())
        binding.rVFrom2.adapter = adapter
        addItem()
        binding.next.setOnClickListener { getData() }
    }

    private fun getData() {
    }

    private fun addItem() {
    }
}