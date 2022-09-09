package com.rchyn.githubapp.ui.fragments.home


import androidx.lifecycle.*
import com.rchyn.githubapp.R
import com.rchyn.githubapp.data.repository.UserRepository
import com.rchyn.githubapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _listUser: MutableLiveData<HomeUiState> = MutableLiveData(HomeUiState())
    val listUser: LiveData<HomeUiState> = _listUser

    fun setSearchQuery(q: String) {
        viewModelScope.launch {
            userRepository.searchUser(q).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _listUser.value = HomeUiState(listUser = result.data)
                    }
                    is Resource.Loading -> {
                        _listUser.value = HomeUiState(isLoading = true)
                    }
                    is Resource.Error -> {
                        if (result.throwable is HttpException) {
                            _listUser.value = HomeUiState(isError = R.string.text_message_error_from_server)
                        } else if (result.throwable is IOException) {
                            _listUser.value = HomeUiState(isError = R.string.text_message_error_from_server)
                        }
                    }
                }
            }
        }
    }
}
