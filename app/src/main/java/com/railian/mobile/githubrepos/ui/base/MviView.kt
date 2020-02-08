package com.railian.mobile.githubrepos.ui.base

import androidx.lifecycle.LiveData

interface MviView<A, S> {
    val actionFlow: LiveData<A>
    var currentState: S
    fun renderOnNewState(state: S, uiTransform: S.() -> Unit) {
        if (currentState == state) return
        currentState = state
        uiTransform(currentState)
    }
}