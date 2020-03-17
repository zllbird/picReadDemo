package com.bird.mm.repository.bg

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.bird.mm.net.ApiService
import com.bird.mm.net.NetworkState
import com.bird.mm.util.XML2List
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

class ItemKeyHomeDetailDataSource (
    private val apiService: ApiService,
    private val firstLink : String
) : ItemKeyedDataSource<String,String>(){

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter and we don't support loadBefore
     * in this example.
     * <p>
     * See BoundaryCallback example for a more complete example on syncing multiple network states.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    var mPage = 1

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String>) {
        mPage ++
        Timber.i("loadAfter params.key ${params.key}  params.pagesize ${params.requestedLoadSize}  mPage $mPage")

        networkState.postValue(NetworkState.LOADING)
//        val request = apiService.girlPicDetail(mPage)
//        val string = request.execute().body()
//        string?.let {
//            val list = XML2List.xml2Model(it)
//            callback.onResult(list)
//        }
        callback.onResult(single2list(params.key,(mPage -1) * params.requestedLoadSize, params.requestedLoadSize))
        networkState.postValue(NetworkState.LOADED)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String>) {
        // do nothing
    }

    override fun getKey(item: String) = item

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String>
    ) {
        val request = apiService.girlPicDetail(firstLink)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        val resposne = request.execute()
        val xmlString = resposne.body()
        val src = XML2List.xml2PageDetailModel(xmlString!!)
        networkState.postValue(NetworkState.LOADED)
        initialLoad.postValue(NetworkState.LOADED)
        callback.onResult(single2list(src,1,params.requestedLoadSize,true))
    }

    fun single2list(src:String,start:Int,pageSize:Int, cantainStart:Boolean = false):List<String>{
        val list = mutableListOf<String>()
        if (cantainStart) list.add(src)
        val replace = src.substring(src.lastIndexOf("/") + 1)
        repeat(pageSize){
            val cache = src.replace(replace, "${start + 1 + it}.jpg")
            list.add(cache)
        }
        return list
    }

}