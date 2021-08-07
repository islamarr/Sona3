/*
 * Last modified 8/7/21 9:08 PM
 */

/*
 * Last modified 8/3/21 5:12 PM
 */

package com.ihsan.sona3.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.data.model.FamilyDataTest
import com.ihsan.sona3.databinding.FragmentHomeBinding
import com.ihsan.sona3.ui.main.MainActivity
import com.ihsan.sona3.utils.toast

@SuppressLint("NonConstantResourceId")
class HomeFragment : BaseFragment<FragmentHomeBinding>(), HomeContract.MainView,
    DataAdapter.OnClickListener {

    private lateinit var presenter: HomePresenter

//    private var dataAdapter by autoCleared<DataAdapter>()

    private lateinit var dataAdapter: DataAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setupOnViewCreated(view: View) {
        val activity = activity as MainActivity
        activity.setHomeItemsVisibility(
            View.VISIBLE,
            DrawerLayout.LOCK_MODE_UNLOCKED
        )

//        presenter = HomePresenter()

//        presenter.getFamilyData()
    }

    override fun successGetFamilyData(pagingData: PagingData<FamilyDataTest>) {

        dataAdapter = DataAdapter()
        dataAdapter.onClickListener = this
        binding.rvData.apply {
            adapter = dataAdapter.apply {
                submitData(lifecycle, pagingData)
            }
            adapter = dataAdapter.withLoadStateAll(
                LoadStateAdapter { dataAdapter.retry() },
                LoadStateAdapter { dataAdapter.retry() },
                LoadStateAdapter { dataAdapter.retry() }
            )
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            setHasFixedSize(true)
            setItemViewCacheSize(25)
        }
    }

    override fun onItemClicked(data: FamilyDataTest, signatureUrl: Long) {
        requireContext().toast(data.title)
    }


}
