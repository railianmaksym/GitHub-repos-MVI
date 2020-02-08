package com.railian.mobile.githubrepos.ui.search.domain

import com.railian.mobile.githubrepos.data.pojo.GitHubReposResponse
import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.data.result.Result

interface SearchRepository {

    suspend fun searchRepos(query: String, page: Int): Result<List<GitHubRepository>>

}