package com.railian.mobile.githubrepos.di.search

import androidx.lifecycle.ViewModel
import com.railian.mobile.githubrepos.di.viewModel.ViewModelKey
import com.railian.mobile.githubrepos.ui.search.dataFlow.SearchDataFlow
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchDataFlowModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchDataFlow::class)
    abstract fun bindSearchDataFlow(searchDataFlow: SearchDataFlow): ViewModel
}