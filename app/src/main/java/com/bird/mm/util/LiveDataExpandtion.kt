package com.bird.mm.util

import androidx.lifecycle.MutableLiveData

inline fun <T> MutableLiveData<T>.notifyObserver(){
    this.value = this.value
}

inline fun <T> MutableLiveData<MutableList<T>>.addItemAndNotify(item:T){
//    val mutableList = mutableListOf<T>()
//    this.value?.let {
//        it.add(item)
//        mutableList.addAll(it)
//    }
//    this.value = mutableList
    this.value?.add(item)
    this.value = this.value
}

inline fun <T> MutableLiveData<MutableList<T>>.addAllItemAndNotify(items:List<T>){
    this.value?.addAll(items)
    this.value = this.value
}