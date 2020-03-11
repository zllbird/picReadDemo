package com.bird.mm.ui.notifications

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
import com.bird.mm.vo.Girl

class NotificationsFragment : HomeFragment() {

    override fun observeData() {
        homeViewModel.setCurrentTDPage(1)
        homeViewModel.usersTD.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun startToDetail(it: Girl) {
//        super.startToDetail(it)
        findNavController().navigate(
            NotificationsFragmentDirections.actionNavigationNotificationsToNavigationHomeSecond(it.link,"Noti")
        )
    }

    override fun loadNextPage() {
//        super.loadNextPage()
        homeViewModel.loadNextTDPage()
    }

}
