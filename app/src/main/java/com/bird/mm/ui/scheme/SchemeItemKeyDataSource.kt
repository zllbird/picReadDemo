package com.bird.mm.ui.scheme

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import com.bird.mm.db.SchemeDao
import com.bird.mm.net.ApiService
import com.bird.mm.net.NetworkState
import com.bird.mm.util.XML2List
import com.bird.mm.vo.Girl
import com.bird.mm.vo.SchemeItem
import timber.log.Timber

class SchemeItemKeyDataSource(
    private val schemeDao: SchemeDao
):ItemKeyedDataSource<Int,SchemeItem>(){
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<SchemeItem>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<SchemeItem>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<SchemeItem>) {
        TODO("Not yet implemented")
    }

    override fun getKey(item: SchemeItem): Int {
        TODO("Not yet implemented")
    }

}