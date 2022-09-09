package com.rchyn.githubapp.util

import com.rchyn.githubapp.data.local.entity.FollowersEntity
import com.rchyn.githubapp.data.local.entity.TypeFollowers
import com.rchyn.githubapp.data.local.entity.UserEntity
import com.rchyn.githubapp.data.remote.dto.Items
import com.rchyn.githubapp.data.remote.dto.UserDetailResponse
import com.rchyn.githubapp.model.User


fun UserEntity.toUser() =
    User(
        id = id,
        url = url,
        username = username,
        name = name,
        type = type,
        avatar = avatarUrl,
        blog = blog,
        company = company,
        twitter = twitter,
        location = location,
        email = email,
        followers = followers,
        following = following,
        repository = repository,
        isFavorite = isFavorite
    )

fun User.toUserEntity() =
    UserEntity(
        id = id,
        url = url,
        username = username,
        name = name,
        type = type,
        avatarUrl = avatar,
        blog = blog,
        company = company,
        twitter = twitter,
        location = location,
        email = email,
        followers = followers,
        following = following,
        repository = repository,
        isFavorite = isFavorite
    )

fun Items.toUser() =
    User(
        id = id,
        username = login,
        type = type,
        avatar = avatarUrl
    )

fun Items.toUserEntity() =
    UserEntity(
        id = id,
        url = "",
        username = login,
        name = "",
        type = type,
        avatarUrl = avatarUrl,
        blog = "",
        company = "",
        twitter = "",
        location = "",
        email = "",
        followers = 0,
        following = 0,
        repository = 0,
        isFavorite = false
    )

fun UserDetailResponse.toUserEntity() =
    UserEntity(
        id = id,
        url = htmlUrl,
        username = login,
        name = name ?: "",
        type = type,
        avatarUrl = avatarUrl,
        blog = blog ?: "",
        company = company ?: "",
        twitter = twitterUsername ?: "",
        location = location ?: "",
        email = email ?: "",
        followers = followers,
        following = following,
        repository = publicRepos,
        isFavorite = false
    )

fun UserEntity.toFollowers(username: String) =
    FollowersEntity(
        username = username,
        idFollowers = id,
        type = TypeFollowers.FOLLOWERS
    )

fun UserEntity.toFollowing(username: String) =
    FollowersEntity(
        username = username,
        idFollowers = id,
        type = TypeFollowers.FOLLOWING
    )