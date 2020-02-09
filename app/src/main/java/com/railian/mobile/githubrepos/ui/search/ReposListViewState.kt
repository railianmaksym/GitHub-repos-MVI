package com.railian.mobile.githubrepos.ui.search

import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.ui.base.ViewState

data class ReposListViewState(
    val repositoryUrl: String = "",
    val repositories: List<GitHubRepository?> = listOf(),
    val isPaging: Boolean = false,
    val loading: Boolean = false,
    val error: Throwable? = null
) : ViewState()