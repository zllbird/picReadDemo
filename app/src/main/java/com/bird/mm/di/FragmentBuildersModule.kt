package com.bird.mm.di

import com.bird.mm.ui.background.BGFragment
import com.bird.mm.ui.dashboard.DashboardFragment
import com.bird.mm.ui.home.HomeDetailFragment
import com.bird.mm.ui.home.HomeFragment
import com.bird.mm.ui.notifications.NotificationsFragment
import com.bird.mm.ui.scheme.SchemeFragment
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

    @ContributesAndroidInjector
    abstract fun contributeBGFragment():BGFragment

    @ContributesAndroidInjector
    abstract fun contributeSchemeFragment():SchemeFragment

}