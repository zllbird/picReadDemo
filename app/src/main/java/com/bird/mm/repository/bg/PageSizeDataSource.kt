package com.bird.mm.repository.bg

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bird.mm.net.ApiService
import com.bird.mm.net.NetworkState
import com.bird.mm.util.XML2List
import com.bird.mm.vo.Girl
import timber.log.Timber

class PageSizeDataSource(
    private val apiService: ApiService ,
    private val name:String = "girl"
):PageKeyedDataSource<Int,Girl>(){

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Girl>
    ) {
        val request = if (name.isEmpty()) apiService.foodListBG(1) else apiService.girlListBG(1)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

//        val resposne = request.execute()
//        val xmlString = resposne.body()
//        val list = XML2List.xml2Model(xmlString!!)
        val list = arrayListOf<Girl>()
        networkState.postValue(NetworkState.LOADED)
        initialLoad.postValue(NetworkState.LOADED)
//        val size = list.size - 5
        callback.onResult(list,-1,2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Girl>) {
        Timber.i("loadAfter params.key ${params.key}  params.pagesize ${params.requestedLoadSize} ")
        networkState.postValue(NetworkState.LOADING)
        val request = if (name.isEmpty()) apiService.foodListBG(params.key) else apiService.girlListBG(params.key)
        val string = request.execute().body()

        string?.let {
            val list = XML2List.xml2Model(it)
            val size = list.size - 5
            callback.onResult(list,params.key+1)
        }
        networkState.postValue(NetworkState.LOADED)
    }



    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Girl>) {
    }

}