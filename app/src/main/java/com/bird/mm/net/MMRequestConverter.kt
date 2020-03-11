package com.bird.mm.net

import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter
import java.io.IOException

internal class MMRequestConverter<T> private constructor() :
    Converter<T, RequestBody> {
    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        return RequestBody.create(MEDIA_TYPE, value.toString())
    }

    companion object {
        val INSTANCE =
            MMRequestConverter<Any>()
        private val MEDIA_TYPE =
            MediaType.get("text/plain; charset=UTF-8")
    }
}