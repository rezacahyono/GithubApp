package com.rchyn.githubapp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rchyn.githubapp.data.Repository
import com.rchyn.githubapp.ui.fragment.detail.DetailUserViewModel
import com.rchyn.githubapp.ui.fragment.follower.FollowersViewModel
import com.rchyn.githubapp.ui.fragment.home.HomeViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FollowersViewModel::class.java)) {
            return FollowersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
