package com.railian.mobile.githubrepos.data.pojo

import com.google.gson.annotations.SerializedName

data class GitHubRepository(
    @SerializedName("created_at")
    val createdAt: String = "",
    val description: String = "",
    val disabled: Boolean = false,
    @SerializedName("forks_count")
    val forksCount: Int = 0,
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("git_url")
    val gitUrl: String = "",
    val homepage: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    val id: Int = 0,
    val language: String = "",
    val name: String = "",
    val owner: Owner = Owner(),
    @SerializedName("private")
    val isPrivate: Boolean = false,
    val score: Double = 0.0,
    @SerializedName("stargazers_count")
    val stargazersCount: Int = 0,
    val url: String = "",
    @SerializedName("watchers_count")
    val watchersCount: Int = 0
)