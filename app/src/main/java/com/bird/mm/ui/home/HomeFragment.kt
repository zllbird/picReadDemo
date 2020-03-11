package com.bird.mm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.databinding.FragmentHomeBinding
import com.bird.mm.di.Injectable
import com.bird.mm.util.autoCleared
import com.bird.mm.vo.Girl
import timber.log.Timber
import javax.inject.Inject

open class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    var binding by autoCleared<FragmentHomeBinding>()
    private var dataBindingComponent = FragmentDataBindingComponent(this)

    var adapter by autoCleared<GirlAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val databinding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false,
            dataBindingComponent
        )

        binding = databinding
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        observeData()

        val girlAdapter = GirlAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ){
            startToDetail(it)
        }
        binding.repoList.adapter = girlAdapter
        adapter = girlAdapter

        binding.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    loadNextPage()
                }
            }
        })

    }

    open fun loadNextPage(){
        homeViewModel.loadNextPage()
    }

    open fun startToDetail(it:Girl) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToHomeSecondFragment(it.link,"Home")
        )
    }

    open fun observeData(){
        homeViewModel.setCurrentPage(1)
        homeViewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}

