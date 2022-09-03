package com.rchyn.githubapp.data.dto

import com.google.gson.annotations.SerializedName
import com.rchyn.githubapp.model.User

data class ResponseUserDetail(

    @SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @SerializedName("login")
    val login: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("blog")
    val blog: String? = null,

    @SerializedName("company")
    val company: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("public_repos")
    val publicRepos: Int? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("followers")
    val followers: Int? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("html_url")
    val htmlUrl: String? = null,

    @SerializedName("following")
    val following: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("location")
    val location: String? = null
) {
    fun toUser() = User(
        id = id?.toLong() ?: 0,
        url = htmlUrl ?: "",
        login = login ?: "",
        name = name ?: "",
        type = type ?: "",
        avatar = avatarUrl ?: "",
        blog = blog ?: "",
        company = company ?: "",
        twitter = twitterUsername ?: "",
        location = location ?: "",
        email = email ?: "",
        followers = followers ?: 0,
        following = following ?: 0,
        repository = publicRepos ?: 0
    )
}
