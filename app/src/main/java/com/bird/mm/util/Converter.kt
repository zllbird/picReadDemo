package com.bird.mm.util

import android.widget.EditText
import androidx.databinding.InverseMethod
import java.text.DateFormat
import java.text.SimpleDateFormat

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

        @JvmStatic fun timeFormat(time:Long):String {
            return SimpleDateFormat("yyyy-MM-dd HH").format(time)
        }

        @JvmStatic fun stringToLong(value: String): Long {
            if (value.isEmpty()) return 0
            return value.toLong()
        }

}