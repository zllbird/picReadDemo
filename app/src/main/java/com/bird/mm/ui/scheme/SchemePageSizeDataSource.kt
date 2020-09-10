package com.bird.mm.ui.scheme

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.room.InvalidationTracker
import com.bird.mm.db.MMDb
import com.bird.mm.db.SchemeDao
import com.bird.mm.net.ApiService
import com.bird.mm.net.NetworkState
import com.bird.mm.util.XML2List
import com.bird.mm.vo.Girl
import com.bird.mm.vo.SchemeItem
import timber.log.Timber
import javax.inject.Inject

class SchemePageSizeDataSource(
    db: MMDb,
    private val schemeDao: SchemeDao
):PageKeyedDataSource<Int,SchemeItem>(){

    init {
        db.invalidationTracker.addObserver(object : InvalidationTracker.Observer("SchemeItem"){
                    override fun onInvalidated(tables: MutableSet<String>) {
                        invalidate()
            }
        })
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SchemeItem>
    ) {
        Timber.i("loadInitial ")
//        val list = schemeDao.query()
        val list = schemeDao.query()
        Timber.i("loadInitial list size is ${list.size}")
        callback.onResult(list,-1,1)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SchemeItem>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SchemeItem>) {
    }


}