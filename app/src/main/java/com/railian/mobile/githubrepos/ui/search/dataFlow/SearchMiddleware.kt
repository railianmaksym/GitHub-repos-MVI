package com.railian.mobile.githubrepos.ui.search.dataFlow

import com.railian.mobile.githubrepos.data.result.Result
import com.railian.mobile.githubrepos.ui.base.Middleware
import com.railian.mobile.githubrepos.ui.search.ReposListViewState
import com.railian.mobile.githubrepos.ui.search.domain.LocalSearchRepository
import com.railian.mobile.githubrepos.ui.search.domain.NetworkSearchRepository
import javax.inject.Inject

class SearchMiddleware @Inject constructor(
    private val networkSearchRepository: NetworkSearchRepository,
    private val localSearchRepository: LocalSearchRepository
) : Middleware<SearchAction, ReposListViewState> {
    override suspend fun performAction(action: SearchAction): ReposListViewState {
        return when (action) {
            is SearchAction.SearchReposAction -> searchRepositories(action)
            //  other actions
        }
    }

    private suspend fun searchRepositories(
        action: SearchAction.SearchReposAction
    ): ReposListViewState {
        val result = when (action.source) {
            SearchAction.DataSource.NETWORK ->
                networkSearchRepository.searchRepos(query = action.query, page = action.page)
            SearchAction.DataSource.LOCAL ->
                localSearchRepository.searchRepos(query = action.query, page = action.page)
        }

        return when (result) {
            is Result.Success ->
                if (result.data.isNotEmpty())
                    ReposListViewState.Data(result.data)
                else
                    ReposListViewState.Empty
            is Result.Error -> ReposListViewState.Error(result.error)
        }
    }
}