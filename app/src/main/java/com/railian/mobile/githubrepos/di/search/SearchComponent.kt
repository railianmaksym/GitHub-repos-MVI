package com.railian.mobile.githubrepos.di.search

import com.railian.mobile.githubrepos.ui.search.SearchFragment
import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class, SearchViewModelModule::class])
@SearchScope
interface SearchComponent {
    fun inject(fragment: SearchFragment)
}