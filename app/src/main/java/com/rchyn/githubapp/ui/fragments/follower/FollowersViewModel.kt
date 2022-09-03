package com.rchyn.githubapp.ui.fragments.follower

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rchyn.githubapp.data.Repository

class FollowersViewModel(
    private val repository: Repository
): ViewModel() {

    private val _username = MutableLiveData<String>()
    val userFollowers = Transformations.switchMap(_username) { username ->
        repository.getUserFollowers(username).asLiveData()
    }
    val userFollowing = Transformations.switchMap(_username){ username ->
        repository.getUserFollowing(username).asLiveData()
    }

    fun setUsername(username: String) {
        _username.value = username
    }
}