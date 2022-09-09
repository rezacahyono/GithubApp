package com.rchyn.githubapp.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rchyn.githubapp.R
import com.rchyn.githubapp.data.repository.UserRepository
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.Resource
import com.rchyn.githubapp.util.toUserEntity
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailUserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _userDetail: MutableLiveData<DetailUiState> = MutableLiveData()
    val userDetail: LiveData<DetailUiState> = _userDetail

    fun setUsername(username: String) {
        viewModelScope.launch {
            repository.getUserDetail(username).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _userDetail.value = DetailUiState(user = result.data)
                    }
                    is Resource.Loading -> {
                        _userDetail.value = DetailUiState(isLoading = true)
                    }
                    is Resource.Error -> {
                        if (result.throwable is HttpException) {
                            _userDetail.value = DetailUiState(isError = R.string.text_message_error_from_server)
                        } else if (result.throwable is IOException) {
                            _userDetail.value = DetailUiState(isError = R.string.text_message_error_from_server)
                        }
                    }
                }
            }
        }
    }

    fun setFavorite(user: User) {
        viewModelScope.launch {
            val userEntity = user.toUserEntity()
            val isNewFavorite = !user.isFavorite
            repository.setUserFavorite(userEntity, isNewFavorite)
        }
    }
}