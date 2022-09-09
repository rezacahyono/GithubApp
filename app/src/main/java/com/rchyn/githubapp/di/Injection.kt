package com.rchyn.githubapp.di

import android.content.Context
import com.rchyn.githubapp.data.local.UserLocalDataSource
import com.rchyn.githubapp.data.local.room.GithubDatabase
import com.rchyn.githubapp.data.remote.UserRemoteDataSource
import com.rchyn.githubapp.data.remote.retrofit.ApiConfig
import com.rchyn.githubapp.data.repository.UserRepository
import com.rchyn.githubapp.data.repository.impl.UserRepositoryImpl

object Injection {
    fun provideUserRepositoryImpl(context: Context): UserRepository {
        val database = GithubDatabase.getInstance(context)
        val userDao = database.userDao()
        val userLocalDataSource = UserLocalDataSource.getInstance(userDao)
        val githubApi = ApiConfig.getInstance()
        val userRemoteDataSource = UserRemoteDataSource.getInstance(githubApi)
        return UserRepositoryImpl.getInstance(userLocalDataSource, userRemoteDataSource)
    }
}