package com.railian.mobile.githubrepos.di.application

import com.railian.mobile.githubrepos.di.network.GitHubApiModule
import com.railian.mobile.githubrepos.di.search.SearchComponent
import com.railian.mobile.githubrepos.di.search.SearchModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, GitHubApiModule::class])
@Singleton
interface AppComponent {
    fun getModule(module: SearchModule): SearchComponent
}