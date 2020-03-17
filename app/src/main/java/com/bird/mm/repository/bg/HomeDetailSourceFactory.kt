package com.bird.mm.repository.bg

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bird.mm.net.ApiService

class HomeDetailSourceFactory (
    private val apiService: ApiService,
    private val link:String
):DataSource.Factory<String,String>(){

    val sourceLiveData = MutableLiveData<ItemKeyHomeDetailDataSource>()

    override fun create(): DataSource<String, String> {
        val source = ItemKeyHomeDetailDataSource(apiService,link)
        sourceLiveData.postValue(source)
        return source
    }


}