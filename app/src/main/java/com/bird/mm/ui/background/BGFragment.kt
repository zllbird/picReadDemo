package com.bird.mm.ui.background

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentGbBinding
import com.bird.mm.di.Injectable
import com.bird.mm.ui.home.GirlAdapter
import com.bird.mm.util.autoCleared
import javax.inject.Inject

class BGFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors


    private val bgModel by viewModels<BGViewModel> { viewModelFactory }

    var binding by autoCleared<FragmentGbBinding>()

    val dataBindingComponent = FragmentDataBindingComponent(this)

    var adapter by autoCleared<GirlAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val databinding = DataBindingUtil.inflate<FragmentGbBinding>(
            layoutInflater,
            R.layout.fragment_gb,
            container,
            false,
            dataBindingComponent
        )
        binding = databinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = GirlAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ){
            findNavController().navigate(
                BGFragmentDirections.actionNavigationBgToNavigationHomeSecond(it.link,"Home")
            )
        }
        binding.repoList.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        bgModel.showSubreddit("start")
        bgModel.bgPhotos.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it){
                val layoutManager = binding.repoList.layoutManager as GridLayoutManager
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION){
                    binding.repoList.scrollToPosition(position)
                }
            }
        })

    }

}