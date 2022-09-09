package com.rchyn.githubapp.data.remote.retrofit

import com.rchyn.githubapp.data.remote.dto.Items
import com.rchyn.githubapp.data.remote.dto.UserDetailResponse
import com.rchyn.githubapp.data.remote.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApi {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): UserResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getUserFollower(
        @Path("username") username: String
    ): List<Items>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): List<Items>
}