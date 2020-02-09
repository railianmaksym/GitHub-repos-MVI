package com.railian.mobile.githubrepos.ui.search.dataFlow

import com.railian.mobile.githubrepos.ui.base.UiAction

sealed class SearchAction : UiAction() {
    class SearchReposAction(
        val query: String,
        val isPaging: Boolean = false,
        val isReload: Boolean = false
    ) : SearchAction()

    enum class DataSource {
        NETWORK,
        LOCAL
    }
}