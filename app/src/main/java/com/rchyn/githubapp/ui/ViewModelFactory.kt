package com.rchyn.githubapp.ui


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rchyn.githubapp.data.repository.UserRepository
import com.rchyn.githubapp.di.Injection
import com.rchyn.githubapp.ui.fragments.detail.DetailUserViewModel
import com.rchyn.githubapp.ui.fragments.favorite.FavoriteViewModel
import com.rchyn.githubapp.ui.fragments.follower.FollowersViewModel
import com.rchyn.githubapp.ui.fragments.home.HomeViewModel

class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FollowersViewModel::class.java)) {
            return FollowersViewModel(userRepository) as T
        }else if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideUserRepositoryImpl(context))
            }.also { INSTANCE = it }
    }
}
