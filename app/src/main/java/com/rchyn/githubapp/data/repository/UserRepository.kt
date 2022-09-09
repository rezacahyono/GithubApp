package com.rchyn.githubapp.data.repository

import com.rchyn.githubapp.data.local.entity.TypeFollowers
import com.rchyn.githubapp.data.local.entity.UserEntity
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun searchUser(query: String): Flow<Resource<List<User>>>

    fun getUserDetail(username: String): Flow<Resource<User>>

    fun getFollowers(username: String, typeFollowers: TypeFollowers): Flow<Resource<List<User>>>

    fun getFollowing(username: String, typeFollowers: TypeFollowers): Flow<Resource<List<User>>>

    fun getUserFavorite(): Flow<Resource<List<User>>>

    suspend fun setUserFavorite(userEntity: UserEntity, isFavorite: Boolean)
}