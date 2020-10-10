package com.bird.mm.util

import android.widget.EditText
import androidx.databinding.InverseMethod

object Converter {

        @InverseMethod("stringToInt")
        @JvmStatic fun intToString(value: Int): String {
            return value.toString()
        }

        @JvmStatic fun stringToInt(value: String): Int {
            if (value.isEmpty()) return 0
            return value.toInt()
        }

        @InverseMethod("stringToLong")
        @JvmStatic fun longToString(value: Long): String {
            return value.toString()
        }

        @JvmStatic fun stringToLong(value: String): Long {
            if (value.isEmpty()) return 0
            return value.toLong()
        }

}