package com.bird.mm.repository

import android.app.Activity
import android.content.Context
import android.util.Xml
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.bird.mm.AppExecutors
import com.bird.mm.db.UserDao
import com.bird.mm.net.*
import com.bird.mm.repository.bg.BGDataSourceFactory
import com.bird.mm.repository.bg.BGPageSizedDataSourceFactory
import com.bird.mm.repository.bg.HomeDetailSourceFactory
import com.bird.mm.util.AbsentLiveData
import com.bird.mm.util.ParseWebUrlHelper
import com.bird.mm.util.Util
import com.bird.mm.util.XML2List
import com.bird.mm.vo.Girl
import com.bird.mm.vo.Listing
import com.bird.mm.vo.Resource
import com.bird.mm.vo.User
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val apiService: ApiService,
    private val apiTDService: ApiTDService,
    private val apiMTWService: ApiMTWService
) {

    @MainThread
    fun cardOfBGBD(bgId:String, pageSize:Int): Listing<Girl>{
        val sourceFactoru = BGDataSourceFactory(apiService,bgId)
        val pagedList = sourceFactoru.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSizeHint = pageSize
            ),
            fetchExecutor = appExecutors.networkIO()
        )
        return Listing(
            pagedList = pagedList,
            networkState = sourceFactoru.sourceLiveData.switchMap {
                it.initialLoad
            }
        )
    }

    @MainThread
    fun cardOfFood(bgId:String, pageSize:Int): Listing<Girl>{
        val sourceFactoru = BGPageSizedDataSourceFactory(apiService,"")
        val pagedList = sourceFactoru.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSizeHint = pageSize
            ),
            fetchExecutor = appExecutors.networkIO()
        )
        return Listing(
            pagedList = pagedList,
            networkState = sourceFactoru.sourceLiveData.switchMap {
                it.initialLoad
            }
        )
    }

    @MainThread
    fun cardOfBGBDPageSize(bgId:String, pageSize:Int): Listing<Girl>{
        val sourceFactoru = BGPageSizedDataSourceFactory(apiService,"girl")
        val pagedList = sourceFactoru.toLiveData(
            config = Config(
                pageSize = 10,
                prefetchDistance = 6,
                enablePlaceholders = false,
                initialLoadSizeHint = 30
            ),
            fetchExecutor = appExecutors.networkIO()
        )
        return Listing(
            pagedList = pagedList,
            networkState = sourceFactoru.sourceLiveData.switchMap {
                it.initialLoad
            }
        )
    }

    fun loadUser(userId: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<User> {
                return userDao.findByUserId(userId)
            }

            override fun createCall(): LiveData<ApiResponse<User>> {
                return apiService.getUser(userId)
            }

        }.asLiveData()
    }

    fun loadHome(curlist:List<Girl>?,current:Int): LiveData<List<Girl>> {
        val da = MutableLiveData<List<Girl>>()
        apiService.girlList(current).observeForever {
            when (it) {
                is ApiSuccessResponse -> {
                    val cache = XML2List.xml2Model(it.body)
                    val list = arrayListOf<Girl>()
                    curlist?.let {ccc ->
                        list.addAll(ccc)
                    }
                    list.addAll(cache)
                    da.value = list
                }
                else -> da.value = arrayListOf()
            }
        }
        return da
    }

    fun parseUrl(activity: Activity , url:String) : LiveData<String>{
        //        val url = "http://www.yhdm.tv/v/4992-10.html"
//        val url = "https://y.qq.com/n/yqq/song/000B4ijs4Ufwql.html"
        val da = MutableLiveData<String>()
        ParseWebUrlHelper.init(activity,url)
        ParseWebUrlHelper.setOnParseListener(object : ParseWebUrlHelper.OnParseWebUrlListener{
            override fun onFindUrl(url: String?) {
                if (Util.checkIsVideo(url)){

                }
            }

            override fun onError(errorMsg: String?) {
            }
        })
        ParseWebUrlHelper.startParse()
        return da
    }

    fun loadPlay(webUrl:String): LiveData<String> {
        val da = MutableLiveData<String>()
        apiService.unKnowPlayUrl(webUrl).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    val body = response.body()
                    body.let {
                        val url = XML2List.xml2UKnowPlayUrl(it!!)
                        da.value = url
                    }
                }
            }
        })
        return da
    }

    fun loadHomeMTW(curlist:List<Girl>?,current:Int): LiveData<List<Girl>> {
        val da = MutableLiveData<List<Girl>>()
        apiMTWService.girlMTWList(current).observeForever {
            when (it) {
                is ApiSuccessResponse -> {
                    val cache = XML2List.xml2MTWModel(it.body)
                    val list = arrayListOf<Girl>()
                    curlist?.let {ccc ->
                        list.addAll(ccc)
                    }
                    list.addAll(cache)
                    da.value = list
                }
                else -> da.value = arrayListOf()
            }
        }
        return da
    }

    fun loadHomeTD(curlist:List<Girl>?,current:Int): LiveData<List<Girl>> {
        val da = MutableLiveData<List<Girl>>()
        apiTDService.girl7160List(current).observeForever {
            when (it) {
                is ApiSuccessResponse -> {
                    val cache = XML2List.xml2TDModel(it.body)
                    val list = arrayListOf<Girl>()
                    curlist?.let {ccc ->
                        list.addAll(ccc)
                    }
                    list.addAll(cache)
                    da.value = list
                }
                else -> da.value = arrayListOf()
            }
        }
        return da
    }

    var cache :MMNetResource? = null

    @Deprecated("use loadHomeDetail(link:String?,page:Int)")
    fun loadHomeDetail(link:String?): LiveData<List<String>> {
        cache = object :MMNetResource(appExecutors){

            override fun createCall(): LiveData<ApiResponse<String>> {
                return apiService.girlDetail(link!!)
            }
        }
        return cache!!.asLiveData()
    }

    @MainThread
    fun loadHomeDetail(link:String?,pageSize: Int):Listing<String>{
        val sourceFactory = HomeDetailSourceFactory(apiService,link!!)
        val pageList = sourceFactory.toLiveData(
            config = Config(
                pageSize = pageSize,
                enablePlaceholders = false,
                initialLoadSizeHint = pageSize
            ),
            fetchExecutor = appExecutors.networkIO()
        )
        return Listing(
            pagedList = pageList,
            networkState = sourceFactory.sourceLiveData.switchMap {
                it.initialLoad
            }
        )
    }


    fun loadHomeTDDetail(link:String?,page:Int): LiveData<String> {
        val da = MutableLiveData<String>()
        val pageString = if (page > 1) "_$page" else ""
        apiTDService.girl7160Detail(link!!,pageString).observeForever{
            when (it) {
                is ApiSuccessResponse -> {
                    da.value = XML2List.xml2TDDetailModel(it.body)
                }
                else -> da.value = ""
            }
        }
        return da
    }

    fun loadMoreDetail(){
        cache?.addValue()
    }

}