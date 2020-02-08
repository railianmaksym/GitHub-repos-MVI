package com.railian.mobile.githubrepos.data.pojo


import com.google.gson.annotations.SerializedName

data class GitHubReposResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerializedName("items")
    val items: List<GitHubRepository> = listOf(),
    @SerializedName("total_count")
    val totalCount: Int = 0
)