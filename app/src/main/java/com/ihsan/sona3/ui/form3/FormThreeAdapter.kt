/*
 * Last modified 10/6/21, 6:02 PM
 */

package com.ihsan.sona3.ui.form3

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.sona3.data.model.form3.Form3Model
import com.ihsan.sona3.databinding.Form3CardBinding

class FormThreeAdapter(
    private val list: ArrayList<Form3Model>,

    ) :
    RecyclerView.Adapter<FormThreeAdapter.ViewHolder>() {

    inner class ViewHolder(binding: Form3CardBinding) : RecyclerView.ViewHolder(binding.root) {
        val incomeType = binding.incomeTypeSpinner
        val cbPhysical = binding.cbPhysical
        val cbInKind = binding.cbInKind
        val value = binding.etValue
        val cardView = binding.cardContainer
        val divider = binding.divider
        val dataContainer = binding.dataContainer
        val btnDelete = binding.btnDeleteData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            Form3CardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        animateCard(holder)
        holder.btnDelete.setOnClickListener {
            holder.value.setText("")
            holder.cbInKind.isChecked = false
            holder.cbPhysical.isChecked = false
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeRemoved(position, list.size)
        }
    }

    private var enabled = false
    private fun animateCard(holder: ViewHolder) {

        holder.cardView.setOnClickListener {
            enabled = !enabled
            if (enabled) {
                holder.dataContainer.layoutTransition = LayoutTransition()
                holder.dataContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                holder.dataContainer.visibility = View.VISIBLE
                holder.divider.visibility = View.VISIBLE
            } else {
                holder.dataContainer.layoutTransition = LayoutTransition()
                holder.dataContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
                holder.dataContainer.visibility = View.GONE
                holder.divider.visibility = View.GONE

            }
        }
    }


    override fun getItemCount(): Int = list.size
}