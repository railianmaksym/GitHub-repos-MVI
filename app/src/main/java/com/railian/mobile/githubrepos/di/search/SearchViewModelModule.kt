package com.railian.mobile.githubrepos.di.search

import androidx.lifecycle.ViewModel
import com.railian.mobile.githubrepos.di.viewModel.ViewModelKey
import com.railian.mobile.githubrepos.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchDataFlow(searchViewModel: SearchViewModel): ViewModel
}