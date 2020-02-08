package com.railian.mobile.githubrepos.ui.search.domain

import com.railian.mobile.githubrepos.data.network.GitHubApiService
import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.data.result.Result
import javax.inject.Inject

class NetworkSearchRepository @Inject constructor(private val gitHubApi: GitHubApiService) :
    SearchRepository {
    override suspend fun searchRepos(query: String, page: Int): Result<List<GitHubRepository>> {
        return try {
            val response =
                gitHubApi.searchReposByQuery(query = query, page = page)
            Result.Success(response.items)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}