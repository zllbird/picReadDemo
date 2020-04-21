package com.bird.mm.ui.scheme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.bird.mm.repository.SchemeRepository
import com.bird.mm.repository.UserRepository
import com.bird.mm.vo.SchemeItem
import javax.inject.Inject

import com.bird.mm.util.addItemAndNotify
import timber.log.Timber

class SchemeViewModel @Inject constructor(private val schemeRepository: SchemeRepository): ViewModel() {


    private val _page = MutableLiveData<Int>()

    val schemes : LiveData<PagedList<SchemeItem>> = _page.switchMap {
        schemeRepository.query().pagedList
    }

    fun setPage(page:Int) {
        if (_page.value != page){
            _page.value = page
        }
    }

    fun addSchemeUrl(schmeUrl:String){
        val item = SchemeItem(0,schmeUrl)
        schemeRepository.insert(item)
        Timber.i(schemes.toString())
//        schemes.value?.dataSource?.invalidate()
    }

    fun addSchemes(items:List<SchemeItem>){
        schemeRepository.insert(items)
//        schemes.value?.dataSource?.invalidate()
    }

    fun updateScheme(item: SchemeItem){
        item.schemeUrl = "我是修改后的"
    }

}