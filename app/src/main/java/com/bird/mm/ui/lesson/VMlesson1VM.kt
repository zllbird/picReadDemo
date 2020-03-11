package com.bird.mm.ui.lesson

import androidx.lifecycle.ViewModel
import kotlin.reflect.KProperty

class VMlesson1VM : ViewModel() {

    var name:String = ""
        get() {
            return "num is $num"
        }

    var num:Int = 0

}

