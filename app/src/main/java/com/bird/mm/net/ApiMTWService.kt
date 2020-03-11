package com.bird.mm.net

import androidx.lifecycle.LiveData
import com.bird.mm.vo.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiMTWService{

    @GET("xinggan/list8{page}.html")
    fun girlMTWList(@Path("page") page: Int): LiveData<ApiResponse<String>>

}