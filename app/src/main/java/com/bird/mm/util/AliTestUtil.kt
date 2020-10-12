package com.bird.mm.util

object AliTestUtil {
    private var isGa = true

    fun getIsGa() : Boolean{
        val result = isGa
        isGa = false
        return result
    }

}