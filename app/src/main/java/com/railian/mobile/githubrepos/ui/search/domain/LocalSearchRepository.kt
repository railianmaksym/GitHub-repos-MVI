package com.railian.mobile.githubrepos.ui.search.domain

import com.railian.mobile.githubrepos.data.local.prefs.UserDataStore
import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.data.result.AsyncResult
import javax.inject.Inject

class LocalSearchRepository @Inject constructor(
    private val userDataStore: UserDataStore
) :
    SearchRepository {
    val userLoggedIn: Boolean
        get() = userDataStore.isUserLoggedIn

    override suspend fun searchRepos(
        query: String,
        page: Int,
        perPage: Int
    ): AsyncResult<List<GitHubRepository>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}