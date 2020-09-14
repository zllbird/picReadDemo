package com.bird.mm.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL
import com.alibaba.android.arouter.facade.annotation.Route
import com.bird.mm.AppExecutors
import com.bird.mm.HomeDetailActivity
import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentHomeBinding
import com.bird.mm.di.Constants
import com.bird.mm.di.Injectable
import com.bird.mm.util.autoCleared
import com.bird.mm.vo.Girl
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.roundToInt

@Route(path = "/main/home")
open class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    var binding by autoCleared<FragmentHomeBinding>()

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
        ){ girl,itemView ->
            startToDetail(girl,itemView)
//            girl.title.set("点击之后")
        }
        binding.repoList.adapter = girlAdapter
        adapter = girlAdapter

        binding.vpList.adapter = girlAdapter
        binding.vpList.orientation = ORIENTATION_VERTICAL

//        binding.ivGirlIcon.setOnClickListener {
//            ViewCompat.setTransitionName(view.findViewById(R.id.iv_girl_icon), "head_image")
//            val item2 = HomeFragmentDirections.actionNavigationHomeToNavigationHomeDetail()
////            val extras = FragmentNavigatorExtras(
////                view.findViewById<ImageView>(R.id.iv_girl_icon) to "head_image"
////            )
//            ActivityOptionsCompat.makeThumbnailScaleUpAnimation(isMenuVisible)
//            ActivityNavigatorExtras(,)
//            view.findNavController().navigate(
//                item2,
//                extras
//            )

//            ViewCompat.setTransitionName(view.findViewById(R.id.iv_girl_icon), "robot")
//            val extras = ActivityNavigatorExtras(
//                ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,binding.ivGirlIcon,"robot")
//            )
//
//            val item3 = HomeFragmentDirections.actionNavigationHomeToNavigationHomeDetail()
//            view.findNavController().navigate(
//                item3,
//                extras
//            )

//            val intent = Intent(activity, HomeDetailActivity::class.java)
//            // create the transition animation - the images in the layouts
//            // of both activities are defined with android:transitionName="robot"
//            val options = ActivityOptions.makeSceneTransitionAnimation(activity, binding.ivGirlIcon, "robot")
//            // start the new activity
//            startActivity(intent, options.toBundle())
//            startActivity(Intent(activity,HomeDetailActivity::class.java))
//        }

//        val obs = binding.ivGirlIcon.clicks().share().map { 1 }
//
//        obs.buffer(Observable.merge(obs.debounce(2000,TimeUnit.MILLISECONDS),obs.take(5)))
//            .subscribe {
//                Timber.i("obs buffer time ${it.size}")
//            }
//        binding.ivGirlIcon.clicks().subscribe { Snackbar.make(view, "单机暂停", Snackbar.LENGTH_SHORT).show() }

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
//        loadAdData()
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


    private fun startToDetail2(girl: Girl, itemView: View) {
        val item = HomeFragmentDirections.actionNavigationHomeToNavigationPlay().setLink(girl.link)
        findNavController().navigate(item)
    }

    open fun startToDetail(it:Girl,view:View) {
        ViewCompat.setTransitionName(view.findViewById(R.id.iv_girl_icon), "head_image")
        ViewCompat.setTransitionName(view.findViewById(R.id.tv_girl_text), "head_text")
        val extras = FragmentNavigatorExtras(
            view.findViewById<ImageView>(R.id.iv_girl_icon) to "head_image" ,
            view.findViewById<ImageView>(R.id.tv_girl_text) to "head_text"
        )
        val item =
            HomeFragmentDirections.actionHomeFragmentToHomeSecondFragment("Home").setItem(it)
//        val item = HomeFragmentDirections.actionHomeFragmentToHomeSecondFragment()
        val item2 = HomeFragmentDirections.actionNavigationHomeToNavigationHomeDetail()
        view.findNavController().navigate(
            item2,
            extras
        )
    }

    open fun observeData(){
        homeViewModel.setBgPage(1)
        homeViewModel.bgList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}

