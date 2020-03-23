package com.bird.mm.net

import androidx.lifecycle.LiveData
import com.bird.mm.vo.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService{
    // todo for test
    @GET("users/{login}")
    fun getUser(@Path("login") login: String): LiveData<ApiResponse<User>>

    @GET("ent/meinvtupian/list_11_{page}.html")
    fun girlList(@Path("page") page: Int): LiveData<ApiResponse<String>>

    @GET("ent/meinvtupian/list_11_{page}.html")
    fun girlListBG(@Path("page") page: Int): Call<String>

    @GET("meishitupian/list_25_{page}.html")
    fun foodListBG(@Path("page") page: Int): Call<String>

    @GET("{url}")
    fun girlDetail(@Path("url") url: String): LiveData<ApiResponse<String>>

    @GET("{url}")
    fun girlPicDetail(@Path("url") url: String): Call<String>

    @GET("beautiful/beijingtupian/list_24_{page}.html")
    fun bgList(@Path("page") page:Int): Call<String>

}