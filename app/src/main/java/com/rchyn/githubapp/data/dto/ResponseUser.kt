package com.rchyn.githubapp.data.dto

import com.google.gson.annotations.SerializedName
import com.rchyn.githubapp.model.User

data class ResponseUser(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val items: List<Items>
)

data class Items(

    @SerializedName("login")
    val login: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("id")
    val id: Int,
) {
    fun toUser() =
        User(
            id = id.toLong(),
            login = login,
            avatar = avatarUrl,
            type = type
        )
}
