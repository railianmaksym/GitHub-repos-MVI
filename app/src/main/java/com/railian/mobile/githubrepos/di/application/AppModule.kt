package com.railian.mobile.githubrepos.di.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.railian.mobile.githubrepos.data.local.prefs.UserDataStore
import com.railian.mobile.githubrepos.util.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun getAppContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun getSharedPreferenses(context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun getUserStorage(sharedPreferences: SharedPreferences): UserDataStore {
        return UserDataStore(sharedPreferences)
    }
}