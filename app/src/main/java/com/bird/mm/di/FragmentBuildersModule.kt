package com.bird.mm.di

import com.bird.mm.ui.dashboard.DashboardFragment
import com.bird.mm.ui.home.HomeDetailFragment
import com.bird.mm.ui.home.HomeFragment
import com.bird.mm.ui.notifications.NotificationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment():HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeDetailFragment():HomeDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragment():DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment

}