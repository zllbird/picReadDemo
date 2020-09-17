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

//    var downloadUrl : String? = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400000pe8GD0He2Fe.m4a?guid=2436595906&vkey=67EC32465E121EE436D1A30C3E5A5073DFF6465A4B8E2402D3C54D66A5A7261EE37C21AC9DDF6C5C07A59D47305CBD8695C5E7FCC258012A&uin=0&fromtag=38"
//    var downloadUrl : String? = "https://f88d10324bc7ddb7d81d76231aa08584.v.smtcdns.net/tlivecloud-cdn.ysp.cctv.cn/A1A7CC8CBFF72E926649A3178F71C5C45DDBD3A87D15B7E3EF72211250358B6B9267278677577FC5F19887708FE6D6534D2C1CC9A87CF177100916580B7983E16413B2DA8BA8D427B4E22A4FE240B1B50B5EA8BC9012D2419B71E435CBAB1820A01A43E54636A731CA802EAF8729B1E341E13637A6FED1C9F8BB352DE463171B/2000204801.m3u8?from=player&svrtime=1600052358&pid=600001814&cdn=5501&revoi=78FBC49341D1E0FBB7DC6F736378C43DEFD688524FAD1EA22450D13E52185CC3D361777633BB227CCB6918ED437CA5C6A1CA5FF406E5D3E8FBEB30D26011031B8AAB9DC149AD93471C622635A1F27A5DDC5D2C591A2E370ED108B222141CE92207625839B7332A58B803D866D95FE4B473BC69E8CB80BCFF0D049F5B565EAF0CBC5F90AB5F6B9E540696A7989806ADF903B32CEF7115D5D999DE756097DC0B25&rand=0.7036630633791905"
    var downloadUrl : String? = ""
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