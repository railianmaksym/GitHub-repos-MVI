package com.railian.mobile.githubrepos.di.search

import com.railian.mobile.githubrepos.data.local.prefs.UserDataStore
import com.railian.mobile.githubrepos.data.network.GitHubApiService
import com.railian.mobile.githubrepos.ui.search.domain.LocalSearchRepository
import com.railian.mobile.githubrepos.ui.search.domain.NetworkSearchRepository
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun getNetworkSearchRepository(gitHubApiService: GitHubApiService): NetworkSearchRepository {
        return NetworkSearchRepository(gitHubApiService)
    }

    @SearchScope
    @Provides
    fun getLocalSearchRepository(userDataStore: UserDataStore): LocalSearchRepository {
        return LocalSearchRepository(userDataStore)
    }
}