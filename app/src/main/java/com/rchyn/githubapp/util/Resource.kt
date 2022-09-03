package com.rchyn.githubapp.util

import androidx.annotation.StringRes

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(@StringRes val message: Int?) : Resource<Nothing>()
}