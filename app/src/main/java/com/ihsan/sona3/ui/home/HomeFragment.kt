/*
 * Last modified 9/16/21 3:19 PM
 */

/*
 * Last modified 8/7/21 9:08 PM
 */

/*
 * Last modified 8/3/21 5:12 PM
 */

package com.ihsan.sona3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.databinding.FragmentHomeBinding
import com.ihsan.sona3.ui.main.MainActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>() {


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupOnViewCreated(view: View) {
        val activity = activity as MainActivity
        activity.setHomeItemsVisibility(
            View.VISIBLE,
            DrawerLayout.LOCK_MODE_UNLOCKED
        )

    }
}
