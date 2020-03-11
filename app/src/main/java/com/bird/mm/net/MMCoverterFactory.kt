package com.bird.mm.net

import androidx.annotation.Nullable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * A [converter][Converter.Factory] for strings and both primitives and their boxed types
 * to `text/plain` bodies.
 */
class MMCoverterFactory private constructor() : Converter.Factory() {
    @Nullable
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return if (type === String::class.java || type === Boolean::class.javaPrimitiveType || type === Boolean::class.java || type === Byte::class.javaPrimitiveType || type === Byte::class.java || type === Char::class.javaPrimitiveType || type === Char::class.java || type === Double::class.javaPrimitiveType || type === Double::class.java || type === Float::class.javaPrimitiveType || type === Float::class.java || type === Int::class.javaPrimitiveType || type === Int::class.java || type === Long::class.javaPrimitiveType || type === Long::class.java || type === Short::class.javaPrimitiveType || type === Short::class.java
        ) {
            MMRequestConverter.INSTANCE
        } else null
    }

    @Nullable
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type === String::class.java) {
            return MMCoverter.StringResponseBodyConverter.INSTANCE
        }
        if (type === Boolean::class.java || type === Boolean::class.javaPrimitiveType) {
            return MMCoverter.BooleanResponseBodyConverter.INSTANCE
        }
        if (type === Byte::class.java || type === Byte::class.javaPrimitiveType) {
            return MMCoverter.ByteResponseBodyConverter.INSTANCE
        }
        if (type === Char::class.java || type === Char::class.javaPrimitiveType) {
            return MMCoverter.CharacterResponseBodyConverter.INSTANCE
        }
        if (type === Double::class.java || type === Double::class.javaPrimitiveType) {
            return MMCoverter.DoubleResponseBodyConverter.INSTANCE
        }
        if (type === Float::class.java || type === Float::class.javaPrimitiveType) {
            return MMCoverter.FloatResponseBodyConverter.INSTANCE
        }
        if (type === Int::class.java || type === Int::class.javaPrimitiveType) {
            return MMCoverter.IntegerResponseBodyConverter.INSTANCE
        }
        if (type === Long::class.java || type === Long::class.javaPrimitiveType) {
            return MMCoverter.LongResponseBodyConverter.INSTANCE
        }
        return if (type === Short::class.java || type === Short::class.javaPrimitiveType) {
            MMCoverter.ShortResponseBodyConverter.INSTANCE
        } else null
    }

    companion object {
        fun create(): MMCoverterFactory {
            return MMCoverterFactory()
        }
    }
}