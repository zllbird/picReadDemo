package com.bird.mm.ui.scheme

import android.os.Environment
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
import java.io.File
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
//        val list = schemeDao.query()

        val list = loadQQMusicList()
        list?.let{
            Timber.i("loadInitial list size is ${it.size}")
            callback.onResult(it,-1,1)
        }

    }

    fun loadQQMusicList() : List<SchemeItem>? {
        val root = Environment.getExternalStoragePublicDirectory("")
        val qqMusicDir = File(root, "/qqmusic/song")

        return if (qqMusicDir.isDirectory) {
            val list = qqMusicDir.listFiles()

            list?.map {
                SchemeItem(0,it.name,it.lastModified(),0,it.path)
            }?.sortedByDescending { it.useTime }
        }else {
            emptyList()
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SchemeItem>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SchemeItem>) {

    }


}