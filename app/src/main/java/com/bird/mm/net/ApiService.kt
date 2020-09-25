package com.bird.mm.net

import androidx.lifecycle.LiveData
import com.bird.mm.vo.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface ApiService{

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

    @GET("http://1205.jeffreyvandiggele.nl/v.php?category=top&viewtype=basic")
    fun uKnowList(@Query("page") page:Int): Call<String>

//    @GET("{url}")
//    fun unKnowPlayUrl(@Path("url") url: String): Call<String>

    @GET
    fun unKnowPlayUrl(@Url url: String): Call<String>


    @Streaming
    @GET
    fun downloadU(@Url url:String) : Call<ResponseBody>

    @GET
    suspend fun aliTestUrl(@Url url: String) : String

}