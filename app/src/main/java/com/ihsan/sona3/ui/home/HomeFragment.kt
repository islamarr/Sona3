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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.model.FamiliesDataList
import com.ihsan.sona3.databinding.FragmentHomeBinding
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.utils.toast


class   HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.View {
    lateinit var navController: NavController
    lateinit var presenter: HomePresenter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupOnViewCreated(view: View) {
        val activity = activity as MainActivity
        activity.setHomeItemsVisibility(
            View.VISIBLE,
            DrawerLayout.LOCK_MODE_UNLOCKED
        )
        navController = Navigation.findNavController(view)
        activity.fab.setOnClickListener { navController.navigate(R.id.action_nav_home_to_formOneFragment) }
        binding.rvData.layoutManager = LinearLayoutManager(requireContext())
        presenter = HomePresenter(this)
        presenter.getData()
    }

    override fun onSuccess(list: List<FamiliesDataList>) {

        val adapter = RecyclerAdapter(list)
        binding.rvData.adapter = adapter


    }

    override fun onFailure(msg: String) {
        requireContext().toast(msg)
    }
}
