package com.bird.mm.net

import androidx.lifecycle.LiveData
import com.bird.mm.vo.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService{
    // todo for test
    @GET("users/{login}")
    fun getUser(@Path("login") login: String): LiveData<ApiResponse<User>>

    @GET("ent/meinvtupian/list_11_{page}.html")
    fun girlList(@Path("page") page: Int): LiveData<ApiResponse<String>>

    @GET("{url}")
    fun girlDetail(@Path("url") url: String): LiveData<ApiResponse<String>>

}