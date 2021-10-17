/*
 * Last modified 10/4/21, 2:09 PM
 */

package com.ihsan.sona3.ui.form3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.form3.Form3Model
import com.ihsan.sona3.databinding.FragmentFormThreeBinding


class FormThreeFragment : BaseFragment<FragmentFormThreeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormThreeBinding
        get() = FragmentFormThreeBinding::inflate

    private lateinit var navController: NavController
    private val itemList = ArrayList<Form3Model>()
    private lateinit var adapter: FormThreeAdapter

    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)

        itemList.add(Form3Model("ahmed", 12, 10.0))
        adapter = FormThreeAdapter(itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        addItem()
        binding.btnNext.setOnClickListener { getData()
            navController.navigate(R.id.action_formThreeFragment_to_formFourFragment)
        }
        binding.btnPrevious.setOnClickListener {
            navController.navigate(R.id.action_formThreeFragment_to_formTwoFragment)

        }
    }

    private fun addItem() {
        binding.addNewCard.setOnClickListener {
            itemList.add(Form3Model("test", 1, 10.0))
            adapter.notifyItemChanged(itemList.size - 1)
        }
    }

    private fun getData() {
        for (i in 0 until itemList.size) {
            val view = binding.recyclerView.getChildAt(i)
            try {

                val value = view.findViewById<EditText>(R.id.etValue)
                val cbPhysical = view.findViewById<RadioButton>(R.id.cbPhysical)
                val cbInKind = view.findViewById<RadioButton>(R.id.cbInKind)
                val spinner = view.findViewById<Spinner>(R.id.incomeTypeSpinner)
                println(spinner.selectedItem.toString())
                if (cbPhysical.isChecked) {
                    println(cbPhysical.text.toString())
                } else if (cbInKind.isChecked) println(cbInKind.text.toString())
                println(value.text.toString())
            } catch (ex: NullPointerException) {
                println(null)
            }
        }
    }

}