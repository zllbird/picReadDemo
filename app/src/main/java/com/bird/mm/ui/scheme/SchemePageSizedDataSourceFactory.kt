package com.bird.mm.ui.scheme

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bird.mm.db.MMDb
import com.bird.mm.db.SchemeDao
import com.bird.mm.ui.scheme.SchemePageSizeDataSource
import com.bird.mm.vo.SchemeItem

class SchemePageSizedDataSourceFactory (
    private val db: MMDb,
    private val schemeDao: SchemeDao
) : DataSource.Factory<Int,SchemeItem>(){

    val sourceLiveData = MutableLiveData<SchemePageSizeDataSource>()

    override fun create(): DataSource<Int, SchemeItem> {
        val source = SchemePageSizeDataSource(db,schemeDao)
        sourceLiveData.postValue(source)
        return source
    }

}