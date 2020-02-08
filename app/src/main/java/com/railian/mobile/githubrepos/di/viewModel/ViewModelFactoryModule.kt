package com.railian.mobile.githubrepos.di.viewModel

import androidx.lifecycle.ViewModelProvider
import com.railian.mobile.githubrepos.di.viewModel.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory):
            ViewModelProvider.Factory
}