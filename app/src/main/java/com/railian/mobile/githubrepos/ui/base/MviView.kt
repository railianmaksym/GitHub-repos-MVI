package com.railian.mobile.githubrepos.ui.base

import androidx.lifecycle.LiveData

interface MviView<A, S> {
    val action: LiveData<A>
    var currentState: S
    fun renderOnNewState(state: S, uiTransform: S.() -> Unit) {
        if (currentState == state) return
        currentState = state
        uiTransform(currentState)
    }
}