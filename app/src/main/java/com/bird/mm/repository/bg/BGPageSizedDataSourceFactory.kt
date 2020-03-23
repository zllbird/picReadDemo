package com.bird.mm.repository.bg

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bird.mm.net.ApiService
import com.bird.mm.vo.Girl

class BGPageSizedDataSourceFactory (
    private val apiService: ApiService,
    private val name:String = "girl"
) : DataSource.Factory<Int,Girl>(){

    val sourceLiveData = MutableLiveData<PageSizeDataSource>()

    override fun create(): DataSource<Int, Girl> {
        val source = PageSizeDataSource(apiService,name)
        sourceLiveData.postValue(source)
        return source
    }

}