package com.railian.mobile.githubrepos.data.network.interceptors

import com.railian.mobile.githubrepos.data.local.prefs.UserDataStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val store: UserDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var url = request.url().toString()
        url = url.replace("%2F", "/")
        request = request.newBuilder().apply {
            url(url)
            header("Authorization", "token ${store.token}")
        }.build()

        return chain.proceed(request)
    }
}
