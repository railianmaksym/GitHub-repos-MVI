package com.railian.mobile.githubrepos.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseMviViewModel<A : UiAction, S : ViewState> : ViewModel() {

    abstract val state: LiveData<S>

    private var actionsData: LiveData<A>? = null

    private val observer = Observer<A> {
        handleUiAction(it)
    }

    fun bind(action: LiveData<A>) {
        actionsData = action
        actionsData?.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        actionsData?.removeObserver(observer)
    }

    protected abstract fun handleUiAction(action: A)
}