package com.rchyn.githubapp.data.local

import com.rchyn.githubapp.data.local.entity.FollowersEntity
import com.rchyn.githubapp.data.local.entity.TypeFollowers
import com.rchyn.githubapp.data.local.entity.UserEntity
import com.rchyn.githubapp.data.local.room.UserDao
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.toUser
import kotlinx.coroutines.flow.*


class UserLocalDataSource private constructor(
    private val userDao: UserDao
) {
    fun getUserByUsername(username: String): Flow<User> =
        userDao.getUserByUsername(username)
            .map { it.toUser() }
            .catch { emit(User()) }

    fun getFollowers(username: String, typeFollowers: TypeFollowers): Flow<List<User>> =
        userDao.getFollowers(username, typeFollowers).map { users ->
            users.map { it.toUser() }
        }

    fun getFollowing(username: String, typeFollowers: TypeFollowers): Flow<List<User>> =
        userDao.getFollowers(username, typeFollowers).map { users ->
            users.map { it.toUser() }
        }

    fun getUserFavorite(): Flow<List<User>> =
        userDao.getUserFavorite().map { users ->
            users.map { it.toUser() }
        }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun insertUsers(users: List<UserEntity>){
        userDao.insertUsers(users)
    }

    suspend fun insertFollowers(followers: List<FollowersEntity>) {
        userDao.insertFollowers(followers)
    }

    suspend fun updateUser(userEntity: UserEntity) {
        userDao.updateUser(userEntity)
    }

    suspend fun isNewFavorite(username: String): Boolean {
        return userDao.isNewFavorite(username)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserLocalDataSource? = null

        fun getInstance(userDao: UserDao): UserLocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserLocalDataSource(userDao)
            }.also { INSTANCE = it }
    }
}