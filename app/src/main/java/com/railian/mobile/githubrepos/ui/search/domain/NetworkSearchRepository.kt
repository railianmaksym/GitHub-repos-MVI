package com.railian.mobile.githubrepos.ui.search.domain

import com.railian.mobile.githubrepos.data.network.GitHubApiService
import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.data.result.AsyncResult
import javax.inject.Inject

class NetworkSearchRepository @Inject constructor(private val gitHubApi: GitHubApiService) :
    SearchRepository {
    override suspend fun searchRepos(
        query: String,
        page: Int,
        perPage: Int
    ): AsyncResult<List<GitHubRepository>> {
        return try {
            val response =
                gitHubApi.searchReposByQuery(query = query, page = page, perPage = perPage)
            AsyncResult.Success(response.items)
        } catch (e: Exception) {
            AsyncResult.Error(e)
        }
    }
}