package com.rchyn.githubapp.ui.fragment.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rchyn.githubapp.data.Repository

class DetailUserViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val userDetail = Transformations.switchMap(_username) { username ->
        repository.getUserDetail(username).asLiveData()
    }

    fun setUsername(username: String) {
        _username.value = username
    }
}