package com.railian.mobile.githubrepos.di.search

import com.railian.mobile.githubrepos.data.network.GitHubApiService
import com.railian.mobile.githubrepos.ui.search.dataFlow.SearchMiddleware
import com.railian.mobile.githubrepos.ui.search.domain.LocalSearchRepository
import com.railian.mobile.githubrepos.ui.search.domain.NetworkSearchRepository
import dagger.Module
import dagger.Provides

@Module
class SearchModule {
    @SearchScope
    @Provides
    fun getSearchMiddleware(
        networkSearchRepository: NetworkSearchRepository,
        localSearchRepository: LocalSearchRepository
    ): SearchMiddleware {
        return SearchMiddleware(networkSearchRepository, localSearchRepository)
    }

    @SearchScope
    @Provides
    fun getNetworkSearchRepository(gitHubApiService: GitHubApiService): NetworkSearchRepository {
        return NetworkSearchRepository(gitHubApiService)
    }

    @SearchScope
    @Provides
    fun getLocalSearchRepository(): LocalSearchRepository {
        return LocalSearchRepository()
    }
}