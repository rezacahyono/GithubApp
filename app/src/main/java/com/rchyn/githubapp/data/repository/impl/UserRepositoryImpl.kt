package com.rchyn.githubapp.data.repository.impl


import com.rchyn.githubapp.data.local.UserLocalDataSource
import com.rchyn.githubapp.data.local.entity.TypeFollowers
import com.rchyn.githubapp.data.local.entity.UserEntity
import com.rchyn.githubapp.data.networkBoundResource
import com.rchyn.githubapp.data.remote.UserRemoteDataSource
import com.rchyn.githubapp.data.repository.UserRepository
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.Resource
import com.rchyn.githubapp.util.toFollowers
import com.rchyn.githubapp.util.toFollowing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class UserRepositoryImpl private constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override fun searchUser(query: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        try {
            val data = userRemoteDataSource.searchUser(query)
            emit(Resource.Success(data))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable))
        }
    }

    override fun getUserDetail(username: String): Flow<Resource<User>> {
        return networkBoundResource(
            query = {
                userLocalDataSource.getUserByUsername(username)
            },
            fetch = {
                userRemoteDataSource.getUserDetail(username)
            },
            saveFetchResult = { user ->
                user.collectLatest {
                    userLocalDataSource.insertUser(it)
                }
            },
            shouldFetch = { user ->
                user.name.isBlank()
            }
        )
    }

    override fun getFollowers(
        username: String,
        typeFollowers: TypeFollowers
    ): Flow<Resource<List<User>>> {
        return networkBoundResource(
            query = {
                userLocalDataSource.getFollowers(username, typeFollowers)
            },
            fetch = {
                userRemoteDataSource.getUserFollowers(username)
            },
            saveFetchResult = { users ->
                users.collectLatest {
                    val followers = it.map { user -> user.toFollowers(username) }
                    userLocalDataSource.inserFollowers(followers)
                    val userEntity = it.map { user ->
                        val isFavorite = userLocalDataSource.isNewFavorite(user.username)
                        user.copy(isFavorite = isFavorite)
                    }
                    userLocalDataSource.insertUsers(userEntity)
                }
            },
            shouldFetch = { users ->
                users.isEmpty()
            }
        )
    }

    override fun getFollowing(
        username: String,
        typeFollowers: TypeFollowers
    ): Flow<Resource<List<User>>> {
        return networkBoundResource(
            query = {
                userLocalDataSource.getFollowing(username, typeFollowers)
            },
            fetch = {
                userRemoteDataSource.getUserFollowing(username)
            },
            saveFetchResult = { users ->
                users.collectLatest {
                    val following = it.map { user -> user.toFollowing(username) }
                    userLocalDataSource.inserFollowers(following)
                    val userEntity = it.map { user ->
                        val isFavorite = userLocalDataSource.isNewFavorite(user.username)
                        user.copy(isFavorite = isFavorite)
                    }
                    userLocalDataSource.insertUsers(userEntity)
                }
            },
            shouldFetch = { users ->
                users.isEmpty()
            }
        )
    }

    override fun getUserFavorite(): Flow<Resource<List<User>>> {
        return networkBoundResource(
            query = {
                userLocalDataSource.getUserFavorite()
            },
            fetch = {},
            saveFetchResult = {}
        )
    }

    override suspend fun setUserFavorite(userEntity: UserEntity, isFavorite: Boolean) {
        withContext(Dispatchers.IO){
            val data = userEntity.copy(isFavorite = isFavorite)
            userLocalDataSource.updateUser(data)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepositoryImpl? = null

        fun getInstance(
            userLocalDataSource: UserLocalDataSource,
            remoteDataSource: UserRemoteDataSource
        ): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepositoryImpl(userLocalDataSource, remoteDataSource)
            }.also { INSTANCE = it }
    }
}