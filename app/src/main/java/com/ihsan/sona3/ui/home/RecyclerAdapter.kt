/*
 * Last modified 9/21/21 9:11 PM
 */

package com.ihsan.sona3.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.FamiliesDataList
import com.ihsan.sona3.databinding.HomeItemViewBinding

class RecyclerAdapter(private val list: List<FamiliesDataList>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(var binding: HomeItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvFamilyName
        var needs = binding.tvNeeds
        var address = binding.tvAddress
        var date = binding.tvDate
        var cardView = binding.cv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.name.text = model.title
        holder.needs.text = model.title
        holder.address.text = model.title
        holder.date.text = model.title
        holder.cardView.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_home_to_detailsFragment)
        }


    }

    override fun getItemCount(): Int = list.size
}