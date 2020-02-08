package com.railian.mobile.githubrepos.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.railian.mobile.githubrepos.ui.base.BaseMviViewModel
import com.railian.mobile.githubrepos.ui.base.ReposListViewState

class SearchViewModel : BaseMviViewModel<SearchAction, ReposListViewState>() {

    override val state: LiveData<ReposListViewState> = MutableLiveData(ReposListViewState())

    override fun handleUiAction(action: SearchAction) {
        when (action) {
            is SearchAction.SearchReposAction -> {
                (state as MutableLiveData).value = state.value?.copy(loading = true)
            }
            is SearchAction.Pagination -> {
            }
        }
    }

}