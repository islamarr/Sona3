/*
 * Last modified 8/6/21 4:48 PM
 */

/*
 * Last modified 8/6/21 4:28 PM
 */

/**
 *  Created by Ahmed Shehatah
 *  ~~~~~~ This Code is For Testing ~~~~~~~
 */

package com.ihsan.sona3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ihsan.sona3.databinding.LoadStateBinding

class LoadStateAdapter(private val replay: () -> Unit) :
    LoadStateAdapter<com.ihsan.sona3.ui.home.LoadStateAdapter.LoadHolder>() {
    inner class LoadHolder(private val binding: LoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.reply.setOnClickListener { replay.invoke() }
        }

        fun setLoadState(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.reply.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.reply.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.reply.visibility = View.GONE
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoadHolder, loadState: LoadState) {
        holder.setLoadState(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadHolder {
        return LoadHolder(
            LoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


}