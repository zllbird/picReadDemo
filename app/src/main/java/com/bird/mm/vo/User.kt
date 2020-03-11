package com.bird.mm.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["userId"])
data class User(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("name")
    val name: String
)