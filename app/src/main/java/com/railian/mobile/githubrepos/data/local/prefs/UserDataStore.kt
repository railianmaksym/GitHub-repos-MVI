package com.railian.mobile.githubrepos.data.local.prefs

import android.content.SharedPreferences

class UserDataStore(preferences: SharedPreferences) {

    var token: String by PreferencesDelegate(preferences, TOKEN, "")
    var username: String by PreferencesDelegate(preferences, USERNAME, "")
    var avatar: String by PreferencesDelegate(preferences, AVATAR, "")

    val isUserLoggedIn: Boolean
        get() = token.isNotEmpty()

    companion object {
        private const val TOKEN = "token"
        private const val USERNAME = "username"
        private const val AVATAR = "avatar"
    }
}