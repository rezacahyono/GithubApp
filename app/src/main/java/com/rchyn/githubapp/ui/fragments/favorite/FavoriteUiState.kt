package com.rchyn.githubapp.ui.fragments.favorite

import androidx.annotation.StringRes
import com.rchyn.githubapp.model.User

data class FavoriteUiState(
    val isLoading: Boolean = false,
    @StringRes
    val isError: Int = 0,
    val listUser: List<User>? = null
)
