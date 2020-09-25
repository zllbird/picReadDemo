package com.bird.mm.repository

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.bird.mm.AppExecutors
import com.bird.mm.db.*
import com.bird.mm.net.ApiMTWService
import com.bird.mm.net.ApiService
import com.bird.mm.net.ApiTDService
import com.bird.mm.ui.scheme.SchemePageSizedDataSourceFactory
import com.bird.mm.vo.Listing
import com.bird.mm.vo.SchemeItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
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
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSizeHint = 10
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
                Timber.i("~~~### onFailure")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Timber.i("~~~### onResponse")
                try {
                    val body = response.body()?.byteStream()
                }catch (t: Throwable){

                }
            }

        })
    }

//    suspend fun alitest(url: String): String {
//        return apiService.aliTestUrl(url)
//    }
//
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