package com.rchyn.githubapp.ui.fragments.detail

import androidx.annotation.StringRes
import com.rchyn.githubapp.model.User

data class DetailUiState(
    val isLoading: Boolean = false,
    @StringRes
    val isError: Int = 0,
    val user: User? = null
)
