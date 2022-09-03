package com.rchyn.githubapp.data.remote

import com.rchyn.githubapp.data.dto.Items
import com.rchyn.githubapp.data.dto.ResponseUser
import com.rchyn.githubapp.data.dto.ResponseUserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): ResponseUser

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): ResponseUserDetail

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String
    ): List<Items>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<Items>
}