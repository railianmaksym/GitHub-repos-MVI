package com.railian.mobile.githubrepos.data.pojo

import com.google.gson.annotations.SerializedName
import com.railian.mobile.githubrepos.util.extensions.round

data class GitHubRepository(
    @SerializedName("created_at")
    val createdAt: String = "",
    val description: String = "",
    val disabled: Boolean = false,
    @SerializedName("forks_count")
    private val forksCount: Int = 0,
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("git_url")
    val gitUrl: String = "",
    val homepage: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = "",
    val id: Int = 0,
    val language: String = "Not specified",
    val name: String = "",
    val owner: Owner = Owner(),
    @SerializedName("private")
    val isPrivate: Boolean = false,
    val score: Double = 0.0,
    @SerializedName("stargazers_count")
    private val stargazersCount: Int = 0,
    val url: String = ""
) {
    val forksCountString: String
        get() = forksCount.thousandString

    val starsCountString: String
        get() = stargazersCount.thousandString

    private val Int.thousandString: String
        get() = if (this > 1000)
            "${(this.toFloat() / 1000).round(1)}k"
        else
            this.toString()
}