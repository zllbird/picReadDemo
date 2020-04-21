package com.bird.mm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bird.mm.MMViewModelFactory
import com.bird.mm.ui.background.BGViewModel
import com.bird.mm.ui.home.HomeDetailViewModel
import com.bird.mm.ui.home.HomeViewModel
import com.bird.mm.ui.scheme.SchemeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BGViewModel::class)
    abstract fun bindBGViewModel(bgViewModel: BGViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeDetailViewModel::class)
    abstract fun bindHomeDetailViewModel(homeDetailViewModel: HomeDetailViewModel) :ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SchemeViewModel::class)
    abstract fun bindSchemeViewModel(schemeViewModel: SchemeViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MMViewModelFactory): ViewModelProvider.Factory
}