package com.bird.mm.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bird.mm.AppExecutors
import com.bird.mm.net.ApiResponse
import com.bird.mm.net.ApiSuccessResponse
import com.bird.mm.vo.Resource
import org.jsoup.Jsoup

abstract class MMNetResource @MainThread constructor(private val appExecutors: AppExecutors){

    val data = MutableLiveData<List<String>>()

    init {
        val apiResponse = createCall()
        apiResponse.observeForever {
            when (it) {
                is ApiSuccessResponse -> {
                    val cache = xml2DetailModel(it.body)
                    data.value = cache
                }
                else -> data.value = arrayListOf()
            }
        }
    }

    private fun xml2DetailModel(xmlString:String): List<String> {
        val doc = Jsoup.parse(xmlString)
        var img = doc.select("div#picBody a img").first()
        var src= img?.attr("src")  ?: ""
        val list = arrayListOf<String>()
//        val indexpre = src.indexOfLast { it.equals("/") }
//        val index = src.indexOfLast { it.equals(".") }
        repeat(10){
            val cache = src.replace("1.jpg", "${it+1}.jpg")
            list.add(cache)
        }
        return list
    }

    @MainThread
    private fun setValue(newValue: List<String>) {
        if (data.value != newValue) {
            data.value = newValue
        }
    }

    @MainThread
    fun addValue() {
        val old = data.value
        val new = mutableListOf<String>()
        new.addAll(old!!)
        val src = new[0]
        val start = new.size
        repeat(10){
            val cache = src.replace("1.jpg", "${start + it}.jpg")
            new.add(cache)
        }
        data.value = new
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<String>>

    fun asLiveData() = data as LiveData<List<String>>

}