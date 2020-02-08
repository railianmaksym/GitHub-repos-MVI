package com.railian.mobile.githubrepos.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {
    @GET("/search/repositories")
    fun searchReposByQuery(
        @Query("q") query: String,
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    )
}