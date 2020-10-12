package com.bird.mm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bird.mm.vo.SchemeItem

@Dao
interface AliGaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schemeItem: SchemeItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schemeItems: List<SchemeItem>)



}