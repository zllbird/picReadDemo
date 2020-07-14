package com.bird.mm.ui.scheme

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bird.mm.vo.Girl
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class TestLifeCycleObserver(val context: Context,val girl: Girl?)  : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun testForResume(){
        Timber.i("testForResume")
        Toast.makeText(context,"testForResume ",Toast.LENGTH_SHORT).show()
        Toast.makeText(context,"testForResume $girl",Toast.LENGTH_SHORT).show()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun testForPause(){
        Timber.i("testForPause")
        Toast.makeText(context,"testForPause ",Toast.LENGTH_SHORT).show()
        Toast.makeText(context,"testForPause $girl",Toast.LENGTH_SHORT).show()
    }

}