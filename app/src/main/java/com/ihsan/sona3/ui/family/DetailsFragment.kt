/*
 * Last modified 9/10/21, 5:11 PM
 */

package com.ihsan.sona3.ui.family

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.databinding.FragmentFamilyDetailsBinding

/**
 * Created by (Ameen Essa) on 9/10/2021
 * Copyright (c) 2021 . All rights reserved.
 * @Ameen.MobileDev@gmail.com
 */
class DetailsFragment : BaseFragment<FragmentFamilyDetailsBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFamilyDetailsBinding
        get() = FragmentFamilyDetailsBinding::inflate

    override fun setupOnViewCreated(view: View) {
        binding.iconPhoneNumber.setOnClickListener(this)
        binding.iconFaceBook.setOnClickListener(this)
        binding.iconWhatsApp.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            R.id.iconPhoneNumber -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:123456")
                startActivity(intent)
            }

            R.id.iconFaceBook -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("www.facebook.com")
                startActivity(intent)
            }

            R.id.iconWhatsApp -> {
            }
        }
    }
}