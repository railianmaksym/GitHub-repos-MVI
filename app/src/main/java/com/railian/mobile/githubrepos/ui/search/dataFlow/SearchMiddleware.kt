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

    private var currentSource = SearchAction.DataSource.NETWORK
    private var currentPage = 1

    override suspend fun performAction(
        action: SearchAction,
        currentState: ReposListViewState
    ): ReposListViewState {
        return when (action) {
            is SearchAction.SearchReposAction -> searchRepositories(
                action.query,
                action.isPaging,
                action.isReload,
                currentState
            )
            //  other actions
        }
    }

    private suspend fun searchRepositories(
        query: String,
        isPaging: Boolean = false,
        isReload: Boolean = false,
        currentState: ReposListViewState
    ): ReposListViewState {
        calculateNewPage(isPaging, isReload)
        val result = when (currentSource) {
            SearchAction.DataSource.NETWORK ->
                networkSearchRepository.searchRepos(query = query, page = currentPage)
            SearchAction.DataSource.LOCAL ->
                localSearchRepository.searchRepos(query = query, page = currentPage)
        }

        return when (result) {
            is Result.Success -> currentState.copy(repositories = result.data)
            is Result.Error -> currentState.copy(error = result.error)
        }
    }

    private fun calculateNewPage(isPaging: Boolean, isReload: Boolean) {
        currentPage = when {
            isPaging -> ++currentPage
            isReload -> 1
            else -> return
        }
    }
}