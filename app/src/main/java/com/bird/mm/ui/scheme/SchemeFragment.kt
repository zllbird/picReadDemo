package com.bird.mm.ui.scheme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bird.mm.AppExecutors
import com.bird.mm.R
import com.bird.mm.binding.FragmentDataBindingComponent
import com.bird.mm.databinding.FragmentSchemeBinding
import com.bird.mm.di.Injectable
import com.bird.mm.util.autoCleared
import com.bird.mm.vo.SchemeItem
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class SchemeFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent = FragmentDataBindingComponent(this)

    val schemeViewModel : SchemeViewModel by viewModels {
        viewModelFactory
    }

    var bind by autoCleared<FragmentSchemeBinding>()

    var adapter by autoCleared<SchemeAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSchemeBinding>(
            inflater,
            R.layout.fragment_scheme,
            container,
            false,
            dataBindingComponent
        )
        bind = binding
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.lifecycleOwner = viewLifecycleOwner
        adapter = SchemeAdapter(
            dataBindingComponent,
            appExecutors
        ) {
            startScheme(it?.schemeUrl)
            updateModel(it)
        }

        bind.repoList.adapter = adapter
        loadData()
    }

    private fun updateModel(item: SchemeItem?) {
        schemeViewModel.updateScheme(item!!)
        adapter.notifyDataSetChanged()
    }

    fun startScheme(url:String?){
        try {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            val uri = Uri.parse(url)
            intent.data = uri
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }catch (t:Throwable){
            Snackbar.make(view!!,"没地方可以跳转",Snackbar.LENGTH_LONG).show()
        }
    }

    private fun loadData() {
        schemeViewModel.schemes.observe(viewLifecycleOwner, Observer{
            Timber.i("loadData ${it.size}")
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            bind.repoList.scrollToPosition(0)
        })
        schemeViewModel.setPage(0)
        bind.addBtn.setOnClickListener {
            Timber.i("click ")
            schemeViewModel.addSchemeUrl(bind.input.text.toString())
        }
//        loadB612()
        bind.addBtnB612.setOnClickListener {
            loadB612()
        }
    }

    private fun loadB612() {
        val items = arrayListOf<SchemeItem>()
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpLabelFeed&labelId=3"))
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpLabelFeed&labelId=3"))
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpSpecialFeed&specialTopicId=100016"))
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpAccountDetail&accountId=10069&mId=chaopai-1234"))
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpSubscribeFeed&mId=chaopai-1234"))
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpactivity&activityId=75"))
        items.add(SchemeItem(0,"b612cnb://chaopai?landing=cpactivitylist&mId=chaopai-1234"))
        items.add(SchemeItem(0,"b612cnb://chaopai?"))
        schemeViewModel.addSchemes(items)
    }

}