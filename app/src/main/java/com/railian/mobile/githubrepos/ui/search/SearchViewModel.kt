package com.railian.mobile.githubrepos.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.railian.mobile.githubrepos.data.DataSource
import com.railian.mobile.githubrepos.ui.search.adapter.GitHubReposAdapter
import com.railian.mobile.githubrepos.ui.search.domain.LocalSearchRepository
import com.railian.mobile.githubrepos.ui.search.domain.NetworkSearchRepository
import com.railian.mobile.githubrepos.util.SingleLiveEvent
import com.railian.mobile.githubrepos.util.extensions.launchIOCoroutine
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val networkSearchRepository: NetworkSearchRepository,
    private val localSearchRepository: LocalSearchRepository
) : ViewModel() {
    private var currentPage = 1
    var isLoadingProcess: Boolean = false
    var currentSource = DataSource.NETWORK
        set(value) {
            field = value
            currentPage = 1
            searchRepositories("")
        }

    val repositoryUrl: SingleLiveEvent<String> = SingleLiveEvent<String>()
    val repositoriesAdapter = GitHubReposAdapter {
        repositoryUrl.value = it
    }

    fun searchRepositories(
        query: String,
        isPaging: Boolean = false,
        isReload: Boolean = false
    ) {
        calculateNewPage(isPaging, isReload)
        repositoriesAdapter.showLoading()
        isLoadingProcess = true
        launchIOCoroutine(
            {
                when (currentSource) {
                    DataSource.NETWORK ->
                        networkSearchRepository.searchRepos(query = query, page = currentPage)
                    DataSource.LOCAL ->
                        localSearchRepository.searchRepos(query = query, page = currentPage)
                }
            },
            {
                isLoadingProcess = false
                if (isPaging)
                    repositoriesAdapter.addItems(it)
                else
                    repositoriesAdapter.repositories = it
            },
            {
                isLoadingProcess = false
                it.printStackTrace()
            }
        )
    }

    private fun calculateNewPage(isPaging: Boolean, isReload: Boolean) {
        currentPage = when {
            isPaging -> ++currentPage
            isReload -> 1
            else -> return
        }
    }
}