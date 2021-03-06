package com.bird.mm

import android.app.Activity
import android.app.Application
import android.os.MessageQueue
import com.alibaba.android.arouter.launcher.ARouter
import com.bird.mm.di.AppInjector
import com.bird.mm.util.MMDebugTree
import com.connectsdk.discovery.DiscoveryManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MMApp : Application() , HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG){
            Timber.plant(MMDebugTree())
            ARouter.openLog()
            ARouter.openDebug()
        }
        AppInjector.init(this)
        DiscoveryManager.init(this)
        ARouter.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}