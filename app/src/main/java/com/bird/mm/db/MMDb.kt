package com.bird.mm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bird.mm.vo.*


@Database(
    entities = [
        User::class,
        Music::class,
        MusicPlayList::class,
        MusicInList::class,
        SchemeItem::class
    ],
    version = 2,
    exportSchema = false
)
abstract class MMDb : RoomDatabase(){

    abstract fun userDao() : UserDao

    abstract fun musicDao() : MusicDao

    abstract fun musicListDao() : MusicPlayListDao

    abstract fun musicInListDao() : MusicInPlayListDao

    abstract fun schemeDao():SchemeDao

}