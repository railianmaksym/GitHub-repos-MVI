package com.railian.mobile.githubrepos.ui.base

import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.ui.base.ViewState

data class ReposListViewState(
    val loading: Boolean = false,
    val repos: List<GitHubRepository> = listOf(),
    val error: Throwable? = null
) : ViewState