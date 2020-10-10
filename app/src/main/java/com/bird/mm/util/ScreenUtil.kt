package com.bird.mm.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import com.bird.mm.MMApp
import java.io.File

class ScreenUtil {

    var sNoncompatDnsity:Float = 0f
    var sNoncompatScaledDensity: Float = 0f

    /**
     * design px screen width
     */
    val sScreenWidthByDesign = 360

    /**
     * call in base activity#onCreate
     */
    fun setCustomDensity(activity: Activity , application: Application){

        val appDisplayMetrics = application.resources.displayMetrics

        if (sNoncompatDnsity == 0f){
            sNoncompatDnsity = appDisplayMetrics.density
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity
            application.registerComponentCallbacks(object :ComponentCallbacks{
                override fun onLowMemory() {
                }

                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig.fontScale > 0){
                        sNoncompatScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

            })
        }

        val targetDensity = (appDisplayMetrics.widthPixels / sScreenWidthByDesign ).toFloat()
        val targetScaledDensity = targetDensity * ( sNoncompatScaledDensity / sNoncompatDnsity)
        val targetDensityDpi = (160 * targetDensity).toInt()
        appDisplayMetrics.density = targetDensity
        appDisplayMetrics.scaledDensity = targetScaledDensity
        appDisplayMetrics.densityDpi = targetDensityDpi

        val acitivtyDisplayMetrics = activity.resources.displayMetrics
        acitivtyDisplayMetrics.density = targetDensity
        acitivtyDisplayMetrics.scaledDensity = targetScaledDensity
        acitivtyDisplayMetrics.densityDpi = targetDensityDpi

    }

}