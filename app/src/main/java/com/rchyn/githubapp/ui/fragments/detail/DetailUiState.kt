package com.rchyn.githubapp.ui.fragments.detail

import com.rchyn.githubapp.model.User

data class DetailUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val user: User? = null
)
