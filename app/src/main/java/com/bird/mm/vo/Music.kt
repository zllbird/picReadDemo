package com.bird.mm.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Music(

    @PrimaryKey(autoGenerate = true) val musicId: Int,

    val musicUrl: String,

    val musicName: String,

    val musicAuther: String,

    val musicLyric: String

)

@Entity
data class MusicPlayList(

    @PrimaryKey val listId: Int,
    val listUrl: String,
    val listIcon: String,
    val listName: String

)

@Entity(
    primaryKeys = ["musicId", "listId"],
    foreignKeys = [
        ForeignKey(
            entity = Music::class,
            parentColumns = ["musicId"],
            childColumns = ["musicId"]
        ),
        ForeignKey(
            entity = MusicPlayList::class,
            parentColumns = ["listId"],
            childColumns = ["listId"]
        )]
)
data class MusicInList(
    val musicId: Int,
    val listId: Int
)