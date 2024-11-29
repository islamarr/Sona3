/*
 * Last modified 10/9/21 4:18 PM
 */

package com.ihsan.sona3.ui.form2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Spinner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.form2.Form2Data
import com.ihsan.sona3.databinding.FragmentFormTwoBinding

class FormTwoFragment : BaseFragment<FragmentFormTwoBinding>(), FormTwoContract.View {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormTwoBinding
        get() = FragmentFormTwoBinding::inflate

    private lateinit var navController: NavController

    private lateinit var adapter: FormTwoAdapter
    private lateinit var presenter: FormTwoPresenter

    override fun setupOnViewCreated(view: View) {
        presenter = FormTwoPresenter(this)
        navController = Navigation.findNavController(view)

        presenter.getMembers()
        binding.rVFrom2.layoutManager = LinearLayoutManager(requireContext())

        addItem()

        binding.next.setOnClickListener {
            getData()
            navController.navigate(R.id.action_formTwoFragment_to_formThreeFragment)
        }
        binding.befor.setOnClickListener {
            navController.navigate(R.id.action_formTwoFragment_to_formOneFragment)

        }
    }

    private fun addItem() {
        binding.addNewPerson.setOnClickListener {
            presenter.addMembers()
        }
    }

    private fun getData() {
        /*for (i in 0 until memberList.size) {
            val view = binding.rVFrom2.getChildAt(i)
            try {
                val nnn = view.findViewById<TextInputLayout>(R.id.TIL_Id)
                val smoker = view.findViewById<RadioButton>(R.id.radio_yes)
                val nonSmoker = view.findViewById<RadioButton>(R.id.radio_no)
                val spinnerED = view.findViewById<Spinner>(R.id.sp_education)
                val spinnerProfil = view.findViewById<Spinner>(R.id.sp_profile)
                val spinnerJOb = view.findViewById<Spinner>(R.id.sp_job)

                nnn.editText

            } catch (ex: NullPointerException) {
                println(null)
            }
        }*/
    }

    override fun onMembersReady(memberList: ArrayList<Form2Data>) {
        adapter = FormTwoAdapter(memberList)
        binding.rVFrom2.adapter = adapter
    }

    override fun onUpdateMembers(memberList: ArrayList<Form2Data>) {
        adapter.notifyItemChanged(memberList.size - 1)
    }
}