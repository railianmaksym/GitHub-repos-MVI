package com.railian.mobile.githubrepos.ui.search

import com.railian.mobile.githubrepos.ui.base.UiAction

sealed class SearchAction : UiAction {
    class SearchReposAction(val query: String) : SearchAction()
    class Pagination(val page: Int) : SearchAction()
}