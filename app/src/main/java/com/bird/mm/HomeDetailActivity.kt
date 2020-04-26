package com.bird.mm

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bird.mm.ui.home.HomeDetailFragment
import kotlinx.android.synthetic.main.activity_home_detail.*

class HomeDetailActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)
        supportPostponeEnterTransition()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content, DetailFragment())
            .addToBackStack("asd")
            .commit()
    }

    override fun onResume() {
        super.onResume()
//        startPostponedEnterTransition()
    }

}