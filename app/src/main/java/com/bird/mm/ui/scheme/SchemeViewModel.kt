package com.bird.mm.ui.scheme

import android.view.View
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.bird.mm.repository.SchemeRepository
import com.bird.mm.repository.UserRepository
import com.bird.mm.vo.SchemeItem
import javax.inject.Inject

import com.bird.mm.util.addItemAndNotify
import com.bird.mm.vo.Girl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SchemeViewModel @Inject constructor(private val schemeRepository: SchemeRepository): ViewModel() {


    private val _user = MutableLiveData<Girl>()

    val userData : LiveData<Girl> = _user

    var downloadUrl : String? = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400000pe8GD0He2Fe.m4a?guid=2436595906&vkey=67EC32465E121EE436D1A30C3E5A5073DFF6465A4B8E2402D3C54D66A5A7261EE37C21AC9DDF6C5C07A59D47305CBD8695C5E7FCC258012A&uin=0&fromtag=38"

    fun startDownload(){
        downloadUrl?.let {
            schemeRepository.download(it)
        }
    }

    fun getUser(){
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO){
                schemeRepository.querySus()
            }
//            _user.value = user
        }
    }

    val userLive = liveData(Dispatchers.IO) {
        emit(Girl("","",""))
    }

    private val _page = MutableLiveData<Int>()

    private val _selectUrl = MutableLiveData<String>()

    val selectUrl = _selectUrl

    val schemes : LiveData<PagedList<SchemeItem>> = _page.switchMap {
        schemeRepository.query().pagedList
    }

//    private val _testSchemeItem = MutableLiveData<Int>()
    private val _testSchemeItem = MutableLiveData<SchemeItem>()

    val testSchemeItem = _testSchemeItem

//    fun settestSchemeItem(value:Int){
//    fun settestSchemeItem(value:Int){
//        _testSchemeItem.value = value
//    }
    fun changetestSchemeItem(){
        _testSchemeItem.value?.schemeUrl = "changed changed"
        _testSchemeItem.value = _testSchemeItem.value
    }

    fun changetestSchemeItems(){
        schemes.value?.get(9)?.schemeUrl = "9 hao weizhi "
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
        item.schemeUrl = "notify"
    }

    fun checkSelectUrl(url:String){
        _selectUrl.value = url
    }


    fun onClick(v:View){
        Timber.i("~~~### onClick")
    }



}