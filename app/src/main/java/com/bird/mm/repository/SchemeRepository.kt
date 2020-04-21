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
import javax.inject.Inject

class SchemeRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db:MMDb,
    private val schemeDao: SchemeDao
) {

    fun insert(schemeItem: SchemeItem){
        appExecutors.diskIO().execute {
            schemeDao.insert(schemeItem)
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

}