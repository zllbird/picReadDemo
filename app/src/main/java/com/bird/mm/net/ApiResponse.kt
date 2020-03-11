package com.bird.mm.net

import retrofit2.Response
import retrofit2.http.Body

sealed class ApiResponse<T>{
    companion object{
        fun <T> create(error:Throwable) : ApiErrorResponse<T>{
            return ApiErrorResponse(error.message  ?: "unknown error" )
        }

        fun <T> create(response:Response<T>):ApiResponse<T>{
            return if (response.isSuccessful){
                val body = response.body()
                if (body == null || response.code() == 204){
                    ApiEmptyResponse()
                }else{
                    ApiSuccessResponse(
                        body = body
                    )
                }
            }else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()){
                    response.message()
                }else{
                    msg
                }
                ApiErrorResponse(errorMsg)
            }
        }
    }
}


class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
):ApiResponse<T>()
{

}


data class ApiErrorResponse<T>(val errorMessage:String) : ApiResponse<T>()