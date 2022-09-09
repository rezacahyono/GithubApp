package com.rchyn.githubapp.data.remote

import com.rchyn.githubapp.data.local.entity.UserEntity
import com.rchyn.githubapp.data.remote.retrofit.GithubApi
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.toUser
import com.rchyn.githubapp.util.toUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRemoteDataSource private constructor(
    private val api: GithubApi
) {
    suspend fun searchUser(query: String): List<User> =
        api.searchUser(query).items.map { it.toUser() }


    fun getUserDetail(username: String): Flow<UserEntity> = flow {
        val data = api.getUserDetail(username).toUserEntity()
        emit(data)
    }.flowOn(Dispatchers.IO)

    fun getUserFollowers(username: String): Flow<List<UserEntity>> = flow {
        val data = api.getUserFollower(username).map { it.toUserEntity() }
        emit(data)
    }.flowOn(Dispatchers.IO)

    fun getUserFollowing(username: String): Flow<List<UserEntity>> = flow {
        val data = api.getUserFollowing(username).map { it.toUserEntity() }
        emit(data)
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var INSTANCE: UserRemoteDataSource? = null


        fun getInstance(api: GithubApi): UserRemoteDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRemoteDataSource(api)
            }.also { INSTANCE = it }
    }
}