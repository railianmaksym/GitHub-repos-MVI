package com.railian.mobile.githubrepos.ui.search

import com.railian.mobile.githubrepos.ui.base.UiAction

sealed class SearchAction : UiAction() {
    class SearchReposAction(val query: String, source: DataSource) : SearchAction()
    class Pagination(val page: Int, source: DataSource) : SearchAction()

    enum class DataSource{
        NETWORK,
        LOCAL
    }
}