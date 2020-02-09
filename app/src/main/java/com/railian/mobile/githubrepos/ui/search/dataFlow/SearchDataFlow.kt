package com.railian.mobile.githubrepos.ui.search.dataFlow

import androidx.lifecycle.viewModelScope
import com.railian.mobile.githubrepos.ui.base.BaseDataFlow
import com.railian.mobile.githubrepos.ui.search.ReposListViewState
import com.railian.mobile.githubrepos.ui.search.adapter.GitHubReposAdapter
import com.railian.mobile.githubrepos.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchDataFlow @Inject constructor(private val searchMiddleware: SearchMiddleware) :
    BaseDataFlow<SearchAction, ReposListViewState>() {

    override val stateFlow: SingleLiveEvent<ReposListViewState> = SingleLiveEvent()
    val repositoriesAdapter = GitHubReposAdapter { url, list ->
        stateFlow.value = stateFlow.value?.copy(repositoryUrl = url, repositories = list)
    }

    override fun handleUiAction(action: SearchAction) {
        stateFlow.value?.copy(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val state = searchMiddleware.performAction(action, stateFlow.value!!)
            withContext(Dispatchers.Main) {
                if (state.isPaging)
                    repositoriesAdapter.addItems(state.repositories)
                else
                    repositoriesAdapter.repositories = state.repositories
                stateFlow.value = state
            }
        }
    }
}