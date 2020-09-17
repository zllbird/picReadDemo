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

    suspend fun querySus() = schemeDao.querySusend()

}