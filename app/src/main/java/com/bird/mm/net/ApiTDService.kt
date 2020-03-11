package com.bird.mm.net

import androidx.lifecycle.LiveData
import com.bird.mm.vo.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiTDService{

    @GET("rentiyishu/list_1_{page}.html")
    fun girl7160List(@Path("page") page: Int): LiveData<ApiResponse<String>>

    @GET("{url}index{page}.html")
    fun girl7160Detail(@Path("url") url: String,@Path("page") page: String) : LiveData<ApiResponse<String>>

}