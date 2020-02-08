package com.railian.mobile.githubrepos.ui.base

import androidx.lifecycle.LiveData

interface MviView<A, S> {
    val action: LiveData<A>

    fun render(state: S)
}