package com.railian.mobile.githubrepos.ui.search

import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.ui.base.ViewState

sealed class ReposListViewState : ViewState() {
    object Empty : ReposListViewState()
    object Loading : ReposListViewState()
    class Data(val repos: List<GitHubRepository>) : ReposListViewState()
    class Error(val error: Throwable) : ReposListViewState()
}