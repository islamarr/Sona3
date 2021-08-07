/*
 * Last modified 8/6/21 5:05 PM
 */

/*
 * Last modified 8/6/21 4:46 PM
 */
/**
 * Created by Ahmed Shehatah
 *  ~~~~~ This Code is For Testing ~~~~~~
 */
package com.ihsan.sona3.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.sona3.data.model.FamilyDataTest
import com.ihsan.sona3.databinding.HomeItemViewBinding

class DataAdapter : PagingDataAdapter<FamilyDataTest, DataAdapter.ViewHolder>(DiffData) {
    interface OnClickListener {
        fun onItemClicked(
            data: FamilyDataTest,
            signatureUrl: Long
        )
    }

    var onClickListener: OnClickListener? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.also { holder.bindItems(it) }
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

    fun withLoadStateAll(
        header: LoadStateAdapter<*>,
        footer: LoadStateAdapter<*>,
        refresh: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            header.loadState = loadStates.prepend
            footer.loadState = loadStates.append
            refresh.loadState = loadStates.refresh
        }
        return ConcatAdapter(refresh, header, this, footer)
    }

    inner class ViewHolder(private val binding: HomeItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(data: FamilyDataTest) {
            val signatureUrl = System.currentTimeMillis()
            /// put glide here

            binding.tvFamilyName.text = data.title
            binding.tvNeeds.text = data.body
            binding.tvAddress.text = data.id.toString()
            binding.tvDate.text = data.userId.toString()

            binding.root.setOnClickListener {
                onClickListener?.onItemClicked(data, signatureUrl)
            }
        }

    }

}

object DiffData : DiffUtil.ItemCallback<FamilyDataTest>() {
    override fun areItemsTheSame(oldItem: FamilyDataTest, newItem: FamilyDataTest): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FamilyDataTest, newItem: FamilyDataTest): Boolean {
        return oldItem == newItem
    }

}