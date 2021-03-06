package com.bird.mm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.viewpager2.widget.ViewPager2
import com.bird.mm.AppExecutors

import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentHomeSecondBinding
import com.bird.mm.di.Injectable
import com.bird.mm.util.autoCleared
import com.bumptech.glide.Glide
import timber.log.Timber
import javax.inject.Inject

class HomeDetailFragment : Fragment() , Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var type:String? = null

    private var binding by autoCleared<FragmentHomeSecondBinding>()
    private val viewModel by viewModels<HomeDetailViewModel> {
        viewModelFactory
    }

    private val bindingComponent = FragmentDataBindingComponent(this)
    private val args: HomeDetailFragmentArgs by navArgs()

    private var adapter by autoCleared<GirlDetailPhotoAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_second, container, false)
        val dataBinding = DataBindingUtil.inflate<FragmentHomeSecondBinding>(
            inflater,
            R.layout.fragment_home_second,
            container,
            false,
            bindingComponent
        )
        this.binding = dataBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = view.findViewById<ImageView>(R.id.iv_girl_icon)
        val text = view.findViewById<TextView>(R.id.tv_girl_text)

        ViewCompat.setTransitionName(image,"head_image")
//        ViewCompat.setTransitionName(text,"head_text")

//        val item = args.item
//        Glide.with(this).load(item?.cover).into(image)
//        text.setText(item?.title)


//        initViewPager()
//        viewModel.setLink(args.link)
//        viewModel.setType2(args.type)
//        binding.lifecycleOwner = viewLifecycleOwner
//        if (viewModel.type == "Home"){
//            viewModel.photos.observe(viewLifecycleOwner, Observer {
//                adapter.submitList(it)
//            })
//        }
//
//        if (viewModel.type == "Noti"){
////            viewModel.loadTDDetailByPosition(1)
//            viewModel.photosTd.observe(viewLifecycleOwner, Observer {
//                Timber.i("it size is ${it.size}")
////                adapter.submitList(it)
//                adapter.notifyDataSetChanged()
//            })
//        }
        activity?.startPostponedEnterTransition()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initViewPager() {
        val adapter = GirlDetailPhotoAdapter(
            dataBindingComponent = bindingComponent,
            appExecutors = appExecutors
        )
        binding.viewPager.adapter = adapter
        this.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
//                    binding.viewPager.postDelayed({
//                        when (viewModel.type){
//                            "Home" -> viewModel.loadNextPage(position)
//                            "Noti" -> viewModel.loadTDDetailByPosition(position+2)
//                        }
//                    },300)
                }
            }
        )
    }
}
