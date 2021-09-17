/*
 * Last modified 9/17/21 9:10 PM
 */

package com.ihsan.sona3.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.sona3.data.model.FamiliesDataList
import com.ihsan.sona3.databinding.HomeItemViewBinding

class FamiliesListAdapter :
    PagingDataAdapter<FamiliesDataList, FamiliesListAdapter.ViewHolder>(DataDifferentiators) {

    inner class ViewHolder(binding: HomeItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvFamilyName
        var needs = binding.tvNeeds
        var address = binding.tvAddress
        var date = binding.tvDate

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
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

    object DataDifferentiators : DiffUtil.ItemCallback<FamiliesDataList>() {

        override fun areItemsTheSame(
            oldItem: FamiliesDataList,
            newItem: FamiliesDataList
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: FamiliesDataList,
            newItem: FamiliesDataList
        ): Boolean {
            return oldItem == newItem
        }
    }
}