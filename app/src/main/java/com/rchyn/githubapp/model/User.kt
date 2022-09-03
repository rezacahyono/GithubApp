package com.rchyn.githubapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id: Long = 0L,
    val url: String = "",
    val login: String = "",
    val name: String = "",
    val type: String = "",
    val avatar: String = "",
    val blog: String = "",
    val company: String = "",
    val twitter: String = "",
    val location: String = "",
    val email: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val repository: Int = 0
): Parcelable
