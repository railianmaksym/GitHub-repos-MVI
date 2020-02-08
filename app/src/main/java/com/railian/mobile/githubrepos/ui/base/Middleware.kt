package com.railian.mobile.githubrepos.ui.base

interface Middleware<A : UiAction, S : ViewState> {
    suspend fun performAction(action: A): S
}