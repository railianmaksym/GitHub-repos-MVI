package com.railian.mobile.githubrepos.di.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.railian.mobile.githubrepos.data.local.prefs.UserDataStore
import com.railian.mobile.githubrepos.data.network.GitHubApiService
import com.railian.mobile.githubrepos.data.network.interceptors.AuthInterceptor
import com.railian.mobile.githubrepos.util.AppConstants
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class GitHubApiModule(private val context: Context) {
    @Provides
    @Singleton
    fun getGithHubApi(userDataStore: UserDataStore): GitHubApiService {
        val gson = GsonBuilder().create()

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(userDataStore))
            .addInterceptor(ChuckInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.API_GITHUB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(GitHubApiService::class.java)
    }
}