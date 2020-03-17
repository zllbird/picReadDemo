package com.bird.mm.ui.home

import android.graphics.Point
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentHomeBinding
import com.bird.mm.di.Constants
import com.bird.mm.di.Injectable
import com.bird.mm.util.autoCleared
import com.bird.mm.vo.Girl
import com.qq.e.ads.banner2.UnifiedBannerADListener
import com.qq.e.ads.banner2.UnifiedBannerView
import com.qq.e.comm.util.AdError
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

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

    var bv: UnifiedBannerView? = null

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

//        binding.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val lastPosition = layoutManager.findLastVisibleItemPosition()
//                if (lastPosition == adapter.itemCount - 1) {
//                    loadNextPage()
//                }
//            }
//        })

        loadAdData()
    }

    private fun loadAdData() {
        bv?.let {
            binding.bannerContainer.removeView(it)
            it.destroy()
        }
        bv = UnifiedBannerView(activity,Constants.AD_APP_ID,Constants.AD_BANNER_POI,
            object : UnifiedBannerADListener{
                override fun onADCloseOverlay() {
                    Timber.i("onADCloseOverlay")
                }

                override fun onADExposure() {
                    Timber.i("onADExposure")
                }

                override fun onADClosed() {
                    Timber.i("onADClosed")
                }

                override fun onADLeftApplication() {
                    Timber.i("onADLeftApplication")
                }

                override fun onADOpenOverlay() {
                    Timber.i("onADOpenOverlay")
                }

                override fun onNoAD(p0: AdError?) {
                    Timber.i("onNoAD ${p0?.errorMsg}")
                }

                override fun onADReceive() {
                    Timber.i("onADReceive")
                }

                override fun onADClicked() {
                    Timber.i("onADClicked")
                }
            })
        bv?.setRefresh(30)
        binding.bannerContainer.addView(bv,getUnifiedBannerLayoutParams())
        bv?.loadAD()
    }

    /**
     * banner2.0规定banner宽高比应该为6.4:1 , 开发者可自行设置符合规定宽高比的具体宽度和高度值
     *
     * @return
     */
    private fun getUnifiedBannerLayoutParams(): FrameLayout.LayoutParams? {
//        WindowManager wm = (WindowManager) this
//            .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        val screenSize = Point()
        val wm = activity?.getSystemService<WindowManager>()
        wm?.defaultDisplay?.getSize(screenSize)
        return FrameLayout.LayoutParams(screenSize.x, (screenSize.x / 6.4f).roundToInt())
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
        homeViewModel.setBgPage(1)
        homeViewModel.bgList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}

