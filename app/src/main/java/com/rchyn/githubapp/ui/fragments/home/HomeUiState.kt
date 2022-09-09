package com.rchyn.githubapp.ui.fragments.home

import androidx.annotation.StringRes
import com.rchyn.githubapp.model.User

data class HomeUiState(
    val isLoading: Boolean = false,
    @StringRes
    val isError: Int = 0,
    val listUser: List<User>? = null
)
