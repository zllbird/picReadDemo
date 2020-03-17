package com.bird.mm.repository.bg

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bird.mm.net.ApiService
import com.bird.mm.vo.Girl

class BGDataSourceFactory (
    private val apiService: ApiService,
    private val girlName:String
) : DataSource.Factory<String,Girl>(){

    val sourceLiveData = MutableLiveData<ItemKeyBGDataSource>()

    override fun create(): DataSource<String, Girl> {
        val source = ItemKeyBGDataSource(apiService,girlName)
        sourceLiveData.postValue(source)
        return source
    }

}