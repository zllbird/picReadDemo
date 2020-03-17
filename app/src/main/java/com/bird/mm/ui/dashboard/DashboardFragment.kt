package com.bird.mm.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bird.mm.R
import com.bird.mm.ui.home.HomeFragment
import com.bird.mm.ui.home.HomeFragmentDirections
import com.bird.mm.vo.Girl

class DashboardFragment : HomeFragment() {

    override fun loadNextPage(){
        homeViewModel.loadNextPage()
    }

    override fun startToDetail(it: Girl) {
        findNavController().navigate(
            DashboardFragmentDirections.actionNavigationDashboardToNavigationHomeSecond(it.link,"Home")
        )
    }

    override fun observeData(){
        homeViewModel.setCurrentPage(20)
        homeViewModel.users.observe(viewLifecycleOwner, Observer {
//            adapter.submitList(it)
        })
    }

}
