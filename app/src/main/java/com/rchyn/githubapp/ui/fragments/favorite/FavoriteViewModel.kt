package com.rchyn.githubapp.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rchyn.githubapp.R
import com.rchyn.githubapp.data.repository.UserRepository
import com.rchyn.githubapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FavoriteViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _listUser: MutableLiveData<FavoriteUiState> = MutableLiveData()
    val listUser: LiveData<FavoriteUiState> = _listUser

    init {
        getFavorite()
    }

    private fun getFavorite(){
        viewModelScope.launch {
            userRepository.getUserFavorite().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _listUser.value = FavoriteUiState(listUser = result.data)
                    }
                    is Resource.Loading -> {
                        _listUser.value = FavoriteUiState(isLoading = true)
                    }
                    is Resource.Error -> {
                        if (result.throwable is HttpException) {
                            _listUser.value = FavoriteUiState(isError = R.string.text_message_error_from_server)
                        } else if (result.throwable is IOException) {
                            _listUser.value = FavoriteUiState(isError = R.string.text_message_error_from_server)
                        }
                    }
                }
            }
        }
    }
}