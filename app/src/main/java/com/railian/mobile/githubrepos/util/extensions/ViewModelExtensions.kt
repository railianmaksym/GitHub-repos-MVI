package com.railian.mobile.githubrepos.util.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.railian.mobile.githubrepos.data.result.AsyncResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T : Any> ViewModel.launchIOCoroutine(
    backgroundTask: suspend () -> AsyncResult<T>,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    try {
        viewModelScope.launch(Dispatchers.IO) {
            val result = backgroundTask()
            withContext(Dispatchers.Main) {
                when (result) {
                    is AsyncResult.Success -> onSuccess(result.data)
                    is AsyncResult.Error -> onError(result.exception)
                }
            }
        }
    } catch (e: Exception) {
        onError(e)
    }
}