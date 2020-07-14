package com.bird.mm.repository.bg

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.ItemKeyedDataSource
import com.bird.mm.net.ApiService
import com.bird.mm.net.ApiSuccessResponse
import com.bird.mm.net.NetworkState
import com.bird.mm.util.XML2List
import com.bird.mm.vo.Girl
import org.jsoup.Jsoup
import timber.log.Timber

class ItemKeyBGDataSource (
    private val apiService: ApiService,
    private val girlName : String
) : ItemKeyedDataSource<String,Girl>(){


    var mPage = 1
    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter and we don't support loadBefore
     * in this example.
     * <p>
     * See BoundaryCallback example for a more complete example on syncing multiple network states.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Girl>) {
        mPage ++
        Timber.i("loadAfter params.key ${params.key}  params.pagesize ${params.requestedLoadSize}  mPage $mPage")

        networkState.postValue(NetworkState.LOADING)
//        val request = apiService.bgList(mPage)
        val request = apiService.uKnowList(mPage)
        val string = request.execute().body()
        string?.let {
            val list = XML2List.xml2BGModel(it)
//            val list = XML2List.xml2UKnowModel(it)
            callback.onResult(list)
        }
        networkState.postValue(NetworkState.LOADED)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Girl>) {
        // do nothing
    }

    override fun getKey(item: Girl) = item.cover

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<Girl>
    ) {
        val request = apiService.bgList(mPage)
//        val request = apiService.uKnowList(mPage)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val resposne = request.execute()
        val xmlString = resposne.body()
        val list = XML2List.xml2BGModel(xmlString!!)
//        val list = XML2List.xml2UKnowModel(xmlString!!)
        networkState.postValue(NetworkState.LOADED)
        initialLoad.postValue(NetworkState.LOADED)
        callback.onResult(list)
//        request.observeForever{
//            when(it){
//                is ApiSuccessResponse -> {
//                    networkState.postValue(NetworkState.LOADED)
//                    initialLoad.postValue(NetworkState.LOADED)
//                    callback.onResult(xml2Model(it.body))
//                }
//                else -> {
//                    networkState.postValue(NetworkState.error(""))
//                    initialLoad.postValue(NetworkState.error(""))
//                }
//            }
//        }
    }



}