package com.bird.mm.repository

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.bird.mm.AppExecutors
import com.bird.mm.db.*
import com.bird.mm.net.*
import com.bird.mm.ui.scheme.SchemePageSizedDataSourceFactory
import com.bird.mm.vo.AliTest
import com.bird.mm.vo.Listing
import com.bird.mm.vo.SchemeItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class SchemeRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db:MMDb,
    private val schemeDao: SchemeDao,
    private val apiService: ApiService
) {

    fun insert(schemeItem: SchemeItem){
        appExecutors.diskIO().execute {
            val result = schemeDao.insert(schemeItem)
        }
    }

    fun insert(schemeItems: List<SchemeItem>){
        appExecutors.diskIO().execute {
            schemeDao.insert(schemeItems)
        }
    }

    fun queryAli() = schemeDao.queryAliTest()

    fun queryALiCount() = schemeDao.queryAliTestCount()

    fun query(): Listing<SchemeItem> {
        val sourceFac = SchemePageSizedDataSourceFactory(db,schemeDao)
        val pageList = sourceFac.toLiveData(
            config = Config(
                pageSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSizeHint = 50
            ),
            fetchExecutor = appExecutors.networkIO()
        )
        return Listing(
            pagedList = pageList
        )
    }

    fun download(url:String){
        apiService.downloadU(url).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Timber.e("~~~### onFailure" )
                Timber.e(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Timber.i("~~~### onResponse")
                try {
                    val body = response.body()?.byteStream()
                    Timber.i("~~~### body size is ${body?.available()}")
                }catch (t: Throwable){
                    Timber.e(t)
                }
            }

        })
    }

    fun deleteAllAliTest(){
        schemeDao.deleteAll()
    }

//    fun showQQList():LiveData<List<SchemeItem>>{
//        val root = Environment.getExternalStoragePublicDirectory(null).absolutePath
//        val qqMusicDir = File(root,"/qqmusic/song")
//        if (qqMusicDir.isDirectory){
//            qqMusicDir.list()
//        }
//    }

//    suspend fun alitest(url: String): String {
//        return apiService.aliTestUrl(url)
//    }
//
//{
//    "appId": "b612",
//    "appVersion": "9.9.0",
//    "stickerId": "1",
//    "os": "android",
//    "sdkVersion": "1.10.0",
//    "exposureRecordList": [
//    ]
//}
    suspend fun alitestSus(url: String): MMResource<String>{
       return try {
            val result = apiService.aliTestPost(url,
                AliTest("b612","9.9.0", listOf(),"android","1.10.0","1"))
            MMResource.create(result)
        }catch (e:Throwable){
            e.printStackTrace()
            MMResource.createError(e)
        }
    }

    fun alitest(url: String,times:Int){
        appExecutors.diskIO().execute { schemeDao.deleteAll() }
        testAli(url,1,times)
    }

    fun testAli(url: String,  current:Int , times: Int){
        if (current > times) return
        apiService.unKnowPlayUrl(url).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Timber.i("~~~### onFailure $current")
                testAli(
                    url,
                    current+1,
                    times
                )

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                Timber.i("~~~### onResponse $current")
                testAli(
                    url,
                    current+1,
                    times
                )
            }
        })
    }

    suspend fun querySus() = schemeDao.querySusend()

}