package com.railian.mobile.githubrepos

import android.app.Application
import com.railian.mobile.githubrepos.di.application.AppComponent
import com.railian.mobile.githubrepos.di.application.AppModule
import com.railian.mobile.githubrepos.di.application.DaggerAppComponent
import com.railian.mobile.githubrepos.di.network.GitHubApiModule

class GitHubReposApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .gitHubApiModule(GitHubApiModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}