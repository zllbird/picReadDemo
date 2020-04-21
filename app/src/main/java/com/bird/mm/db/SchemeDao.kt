package com.bird.mm.db

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bird.mm.vo.SchemeItem

@Dao
interface SchemeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schemeItem: SchemeItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schemeItems: List<SchemeItem>)

    @Query("SELECT * FROM SchemeItem ORDER BY id DESC")
    fun query():List<SchemeItem>
}