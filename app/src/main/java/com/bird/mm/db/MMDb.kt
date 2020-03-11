package com.bird.mm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bird.mm.vo.User


@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MMDb : RoomDatabase(){

    abstract fun userDao() : UserDao

}