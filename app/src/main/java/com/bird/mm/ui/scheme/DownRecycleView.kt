package com.bird.mm.ui.scheme

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class DownRecycleView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attr) {

    var downY: Float = 0f

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> downY = ev.y
            MotionEvent.ACTION_MOVE -> {
                val moveY = ev.y - downY
                if (moveY < 0) {

                } else {
                    // 上滑
                    return true
                }
                downY = ev.y
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}