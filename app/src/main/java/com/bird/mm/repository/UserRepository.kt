package com.bird.mm.repository

import android.util.Xml
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bird.mm.AppExecutors
import com.bird.mm.db.UserDao
import com.bird.mm.net.*
import com.bird.mm.util.AbsentLiveData
import com.bird.mm.vo.Girl
import com.bird.mm.vo.Resource
import com.bird.mm.vo.User
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
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
                    val cache = xml2Model(it.body)
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

    fun loadHomeMTW(curlist:List<Girl>?,current:Int): LiveData<List<Girl>> {
        val da = MutableLiveData<List<Girl>>()
        apiMTWService.girlMTWList(current).observeForever {
            when (it) {
                is ApiSuccessResponse -> {
                    val cache = xml2MTWModel(it.body)
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
                    val cache = xml2TDModel(it.body)
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

    fun loadHomeDetail(link:String?): LiveData<List<String>> {
        cache = object :MMNetResource(appExecutors){

            override fun createCall(): LiveData<ApiResponse<String>> {
                return apiService.girlDetail(link!!)
            }
        }
        return cache!!.asLiveData()
    }

    fun loadHomeTDDetail(link:String?,page:Int): LiveData<String> {
        val da = MutableLiveData<String>()
        val pageString = if (page > 1) "_$page" else ""
        apiTDService.girl7160Detail(link!!,pageString).observeForever{
            when (it) {
                is ApiSuccessResponse -> {
                    da.value = xml2TDDetailModel(it.body)
                }
                else -> da.value = ""
            }
        }
        return da
    }

    private fun xml2TDDetailModel(body: String): String {
        val doc = Jsoup.parse(body)
        var img = doc.select("div p a img").first()
        var src= img?.attr("src")  ?: ""
//        val list = arrayListOf<String>()
//        list.add
        return src
    }

    fun loadMoreDetail(){
        cache?.addValue()
    }

    private fun xml2Model(xmlString:String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
        val doc = Jsoup.parse(xmlString)
        var box = doc.select("div.MeinvTuPianBox").first()
        val imgs = box.select("li a")
        return imgs.map {
            val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
            val title = it.attr("title")
            val img = it.select("i img").first()
            var src= img?.attr("src")  ?: ""
            Timber.i("img $src")
            Girl(title,src,link)
        }.filter {
            !it.cover.isNullOrEmpty()
        }
    }

    private fun xml2MTWModel(xmlString:String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
        val doc = Jsoup.parse(xmlString)
//        var box = doc.select("div.MeinvTuPianBox").first()
        val imgs = doc.select("li div a")
        return imgs.map {
            val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
            val title = it.attr("title")
            val img = it.select("img").first()
            var src= img?.attr("src")  ?: ""
            Timber.i("img $src")
            Girl(title,src,link)
        }.filter {
            !it.cover.isNullOrEmpty()
        }
    }

    private fun xml2TDModel(xmlString:String): List<Girl> {
//        val result = String(xmlString.toByteArray(), charset("gb2312"))
        val doc = Jsoup.parse(xmlString)
//        var box = doc.select("div.MeinvTuPianBox").first()
        val imgs = doc.select("li a")
        return imgs.map {
            val link = it.attr("href")
//            val title = String(it.attr("title").toByteArray(), charset("gb2312"))
            val title = it.attr("title")
            val img = it.select("img").first()
            var src= img?.attr("src")  ?: ""
            Timber.i("img $src")
            Girl(title,src,link)
        }.filter {
            !it.cover.isNullOrEmpty()
        }
    }

}