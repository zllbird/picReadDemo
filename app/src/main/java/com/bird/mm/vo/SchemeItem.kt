package com.bird.mm.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SchemeItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var schemeUrl:String
)