package com.rchyn.githubapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @SerializedName("login")
    val login: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("blog")
    val blog: String? = null,

    @SerializedName("company")
    val company: String? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("following")
    val following: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: String? = null
)