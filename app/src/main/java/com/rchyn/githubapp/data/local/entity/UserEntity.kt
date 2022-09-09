package com.rchyn.githubapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val url: String,
    val username: String,
    val name: String,
    val type: String,
    val avatarUrl: String,
    val blog: String,
    val company: String,
    val twitter: String,
    val location: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val repository: Int,
    val isFavorite: Boolean
)