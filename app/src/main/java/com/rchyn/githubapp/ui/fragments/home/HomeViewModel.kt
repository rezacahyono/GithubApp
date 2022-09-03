package com.rchyn.githubapp.ui.fragments.home

import androidx.lifecycle.*
import com.rchyn.githubapp.data.Repository
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.util.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _listUser: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val listUser: LiveData<Resource<List<User>>> = _listUser

    fun setSearchQuery(q: String) {
        viewModelScope.launch {
            repository.searchUser(q).collectLatest { result ->
                when (result) {
                    is Resource.Success -> _listUser.value = Resource.Success(result.data)
                    is Resource.Loading -> _listUser.value = Resource.Loading
                    is Resource.Error -> _listUser.value = Resource.Error(result.message)
                }
            }
        }
    }


}
