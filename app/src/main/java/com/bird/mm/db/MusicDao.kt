package com.bird.mm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bird.mm.vo.Music
import com.bird.mm.vo.MusicInList
import com.bird.mm.vo.MusicPlayList

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(music: Music)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(musics:List<Music>)

}

@Dao
interface MusicPlayListDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(musicPlayList: MusicPlayList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(musicPlayLists: List<MusicPlayList>)


}

@Dao
interface MusicInPlayListDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(musicInList: MusicInList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(musicInLists: List<MusicInList>)

}