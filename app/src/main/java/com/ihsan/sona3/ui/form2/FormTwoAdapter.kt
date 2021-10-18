/*
 * Last modified 10/9/21 4:18 PM
 */

package com.ihsan.sona3.ui.form2

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.sona3.data.model.form2.Form2Data
import com.ihsan.sona3.databinding.OneItemForm2Binding

class FormTwoAdapter(private val list: ArrayList<Form2Data>) :
    RecyclerView.Adapter<FormTwoAdapter.ViewHolder>() {
    private var enabled = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OneItemForm2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FormTwoAdapter.ViewHolder, position: Int) {
        animateCard(holder)
        holder.delete.setOnClickListener {
            holder.name?.setText("")
            holder.nationalId.setText("")
            holder.phoneNum1.setText("")
            holder.phoneNum2.setText("")
            holder.numOfQuran.setText("")
            holder.workIn.setText("")
            holder.NotSmoker.isChecked = false
            holder.smoker.isChecked = false
//            list.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeRemoved(position, list.size)
            holder.ConsContainer.layoutTransition = LayoutTransition()
            holder.ConsContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            holder.ConsContainer.visibility = View.GONE
            holder.div.visibility = View.GONE
        }
        val form2Data: Form2Data = list[position]
        if (position != 0) {
            holder.cardlable.setText("اضافه فرد ")
        }


    }

    private fun animateCard(holder: FormTwoAdapter.ViewHolder) {
        holder.cardContainer.setOnClickListener {
            enabled = !enabled
            if (enabled) {
                holder.ConsContainer.layoutTransition = LayoutTransition()
                holder.ConsContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                holder.ConsContainer.visibility = View.VISIBLE
                holder.div.visibility = View.VISIBLE
            } else {
                holder.ConsContainer.layoutTransition = LayoutTransition()
                holder.ConsContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                holder.ConsContainer.visibility = View.GONE
                holder.div.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(binding: OneItemForm2Binding) : RecyclerView.ViewHolder(binding.root) {
        val cardContainer = binding.card2Container
        val cardlable = binding.cardLabel
        val ConsContainer = binding.form2Container
        val div = binding.divider
        val name = binding.TILName.editText
        val nationalId = binding.nationalId
        val phoneNum1 = binding.EtPhoneNum
        val phoneNum2 = binding.EtPhoneNum2
        val profile = binding.spProfile
        val education = binding.spEducation
        val job = binding.spJob
        val workIn = binding.EtWorkIn
        val NotSmoker = binding.radioNo
        val smoker = binding.radioYes
        val numOfQuran = binding.EtQuran
        val delete = binding.btnDeleteData
    }


}