package com.railian.mobile.githubrepos.ui.search.domain

import com.railian.mobile.githubrepos.data.pojo.GitHubRepository
import com.railian.mobile.githubrepos.data.result.Result

class LocalSearchRepository :
    SearchRepository {
    override suspend fun searchRepos(query: String, page: Int): Result<List<GitHubRepository>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}