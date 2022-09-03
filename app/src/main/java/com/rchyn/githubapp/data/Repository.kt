package com.rchyn.githubapp.data

import android.content.res.Resources
import com.rchyn.githubapp.R
import com.rchyn.githubapp.data.remote.GithubApi
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class Repository private constructor(
    private val api: GithubApi
) {
    fun searchUser(query: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        try {
            val data = api.searchUser(query).items.map { it.toUser() }
            if (data.isNotEmpty()) {
                emit(Resource.Success(data))
            } else {
                emit(
                    Resource.Error(
                        message = R.string.text_message_not_found
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_from_server
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_internet_connection
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    fun getUserDetail(username: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        try {
            val data = api.getUserDetail(username).toUser()
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_from_server
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_internet_connection
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    fun getUserFollowers(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        try {
            val data = api.getUserFollower(username).map { it.toUser() }
            if (data.isNotEmpty()) {
                emit(Resource.Success(data))
            } else {
                emit(
                    Resource.Error(
                        message = R.string.text_message_not_found
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_from_server
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_internet_connection
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    fun getUserFollowing(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        try {
            val data = api.getUserFollowing(username).map { it.toUser() }
            if (data.isNotEmpty()) {
                emit(Resource.Success(data))
            } else {
                emit(
                    Resource.Error(
                        message = R.string.text_message_not_found
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_from_server
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = R.string.text_message_error_internet_connection
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(api: GithubApi): Repository {
            return INSTANCE ?: synchronized(this) {
                val instance = Repository(api)
                INSTANCE = instance
                instance
            }
        }
    }
}