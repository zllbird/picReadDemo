package com.bird.mm.ui.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bird.mm.repository.SchemeRepository
import com.bird.mm.vo.SchemeItem
import javax.inject.Inject

class TitleViewModel @Inject constructor(private val schemeRepository: SchemeRepository): ViewModel() {

    var webUrl : String = ""

    var showList: Boolean = true

    private val _webStatus = MutableLiveData<Int>()

    val webStatus = _webStatus

//    private var _urls : LiveData<PagedList<SchemeItem>>? = null
    private var _urls =MutableLiveData<ArrayList<SchemeItem>>()

    val urls = _urls

    fun loadUrl(){
        _webStatus.value = 1
    }

    fun initUrls(){
        _urls.value = arrayListOf()
    }

    fun addUrl(url:String){
         schemeRepository.insert(SchemeItem(0,url))
        _urls.value?.add(SchemeItem(0,url))
    }


}