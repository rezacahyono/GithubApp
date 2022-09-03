package com.rchyn.githubapp

import android.app.Application
import com.rchyn.githubapp.data.Repository
import com.rchyn.githubapp.data.remote.ApiConfig
import com.rchyn.githubapp.data.remote.GithubApi

class GithubApplication: Application() {
    private val githubApi: GithubApi by lazy { ApiConfig.getInstance() }
    val repository: Repository by lazy { Repository.getInstance(githubApi) }
}