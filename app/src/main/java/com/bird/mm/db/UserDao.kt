package com.bird.mm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bird.mm.vo.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:User)

    @Query("SELECT * FROM user Where userId = :userId")
    fun findByUserId(userId:String):LiveData<User>

}