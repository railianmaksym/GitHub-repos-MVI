package com.railian.mobile.githubrepos.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseDataFlow<A : UiAction, S : ViewState> : ViewModel() {

    abstract val stateFlow: LiveData<S>

    private var actionFlow: LiveData<A>? = null

    private val observer = Observer<A> {
        handleUiAction(it)
    }

    fun bind(action: LiveData<A>) {
        actionFlow = action
        actionFlow?.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        actionFlow?.removeObserver(observer)
    }

    protected abstract fun handleUiAction(action: A)
}