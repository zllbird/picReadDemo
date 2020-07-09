package com.bird.mm.vo

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Girl (
    var title: ObservableField<String>,
//    var title: String,
    val cover: String,
    val link: String
) : Parcelable , BaseObservable() {
    constructor(title:String,cover: String,link: String) : this(ObservableField(title),cover, link)

    fun chageTitle(newTitle : String){
        title.set(newTitle)
    }

}