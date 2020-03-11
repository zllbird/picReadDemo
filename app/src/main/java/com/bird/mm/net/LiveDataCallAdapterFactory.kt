package com.bird.mm.net

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.IllegalArgumentException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory(){
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java){
            return null
        }
        val observerType = getParameterUpperBound(0,returnType as ParameterizedType)
        val rawObserverType = getRawType(observerType)
        if (rawObserverType != ApiResponse::class.java){
            throw IllegalArgumentException("type must be a resource")
        }

        if (observerType !is ParameterizedType){
            throw IllegalArgumentException("resource must be ParameterizedType")
        }

        val bodyType = getParameterUpperBound(0,observerType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}