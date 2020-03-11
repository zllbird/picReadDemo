package com.bird.mm.ui.lesson

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bird.mm.R
import kotlinx.android.synthetic.main.activity_viewmodel1lesson.*

class ViewModelLesson1Activity : AppCompatActivity(){

    private val vm:VMlesson1VM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel1lesson)
        tv_title.text = vm.name
    }

}