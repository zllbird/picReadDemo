package com.bird.mm.net

sealed class MMResource<T> {

    companion object {

        fun <T> createError(e:Throwable) : MMResource<T>{
            return MMResourceError<T>(e.message ?: "UnKnowError")
        }

        fun <T> create(data:T) : MMResource<T>{
            return MMResourceSuccess(data)
        }

        fun <T> createEmpty() : MMResource<T> {
            return MMResourceEmpty()
        }

        fun <T> createLoading() : MMResource<T> {
            return MMResourceLoading()
        }

    }

}

class  MMResourceLoading<T>() : MMResource<T>()
class  MMResourceEmpty<T>() : MMResource<T>()
data class MMResourceSuccess<T>(val data:T) : MMResource<T>()
data class MMResourceError<T>(val error:String) : MMResource<T>()