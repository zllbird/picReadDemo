package com.bird.mm.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Girl(
    val title: String,
    val cover: String,
    val link: String
) : Parcelable