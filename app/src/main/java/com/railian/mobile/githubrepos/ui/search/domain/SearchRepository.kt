package com.railian.mobile.githubrepos.ui.search.domain

import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.data.result.AsyncResult

interface SearchRepository {

    suspend fun searchRepos(
        query: String,
        page: Int,
        perPage: Int = 30
    ): AsyncResult<List<GitHubRepository>>

}