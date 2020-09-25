package com.bird.mm.db

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.*
import com.bird.mm.vo.SchemeItem

@Dao
interface SchemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schemeItem: SchemeItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schemeItems: List<SchemeItem>)

    @Query("SELECT * FROM SchemeItem ORDER BY id DESC")
    fun query():List<SchemeItem>

    @Query("SELECT * FROM SchemeItem ORDER BY id DESC")
    suspend fun querySusend():List<SchemeItem>

    @Query("DELETE FROM SchemeItem")
    fun deleteAll()

    @Query("SELECT * FROM SchemeItem")
    fun queryAliTest():LiveData<List<SchemeItem>>

    @Query("SELECT COUNT(*) FROM SchemeItem")
    fun queryAliTestCount():LiveData<Int>

}