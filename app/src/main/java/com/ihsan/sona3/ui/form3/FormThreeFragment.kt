/*
 * Last modified 10/4/21, 2:09 PM
 */

package com.ihsan.sona3.ui.form3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.databinding.Form3CardBinding
import com.ihsan.sona3.databinding.FragmentFormThreeBinding


class FormThreeFragment : BaseFragment<FragmentFormThreeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFormThreeBinding
        get() = FragmentFormThreeBinding::inflate
    private lateinit var navController: NavController
    private var isvisible = false
    private val views = ArrayList<Form3CardBinding>()
    override fun setupOnViewCreated(view: View) {
        navController = Navigation.findNavController(view)
        binding.cardContainer.setOnClickListener {
            cardAnimation()
        }
        binding.addNewCard.setOnClickListener { addView() }
        binding.btnNext.setOnClickListener { getValues() }
    }

    private fun addView() {
        val view = Form3CardBinding.inflate(layoutInflater)
        binding.layoutContainer.addView(view.root)
        views.add(view)
        var v = false
        view.cardContainer.setOnClickListener {
            v = !v

            if (v) {
                view.divider.visibility = View.VISIBLE
                view.dataContainer.visibility = View.VISIBLE
            } else {
                view.divider.visibility = View.GONE
                view.dataContainer.visibility = View.GONE

            }

        }
        view.btnDeleteData.setOnClickListener {
            binding.layoutContainer.removeView(view.root)
            views.remove(view)
        }
    }

    private fun cardAnimation() {
        isvisible = !isvisible
        if (isvisible) {

            binding.divider.visibility = View.VISIBLE
            binding.dataContainer.visibility = View.VISIBLE
        } else {
            binding.divider.visibility = View.GONE
            binding.dataContainer.visibility = View.GONE
        }
    }

    private fun getValues() {
        for (view in views) {
            println(view.etValue.text.toString())
        }
    }


}