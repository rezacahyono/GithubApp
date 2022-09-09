package com.rchyn.githubapp.ui.fragments.follower

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rchyn.githubapp.data.local.entity.TypeFollowers
import com.rchyn.githubapp.data.repository.UserRepository

class FollowersViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _username = MutableLiveData<String>()
    val userFollowers = Transformations.switchMap(_username) { username ->
        userRepository.getFollowers(username, TypeFollowers.FOLLOWERS).asLiveData()
    }
    val userFollowing = Transformations.switchMap(_username){ username ->
        userRepository.getFollowing(username, TypeFollowers.FOLLOWING).asLiveData()
    }

    fun setUsername(username: String) {
        _username.value = username
    }
}