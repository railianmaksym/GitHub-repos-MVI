package com.railian.mobile.githubrepos.ui.search.dataFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.railian.mobile.githubrepos.ui.base.BaseDataFlow
import com.railian.mobile.githubrepos.ui.search.ReposListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchDataFlow @Inject constructor(private val searchMiddleware: SearchMiddleware) :
    BaseDataFlow<SearchAction, ReposListViewState>() {

    override val stateFlow: LiveData<ReposListViewState> = MutableLiveData()

    override fun handleUiAction(action: SearchAction) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = searchMiddleware.performAction(action)
            withContext(Dispatchers.Main) {
                (stateFlow as MutableLiveData).value = state
            }
        }
    }
}