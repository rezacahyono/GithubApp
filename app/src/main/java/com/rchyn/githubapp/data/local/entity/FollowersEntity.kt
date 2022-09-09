package com.rchyn.githubapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FollowersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val idFollowers: Int,
    val type: TypeFollowers
)
enum class TypeFollowers {
    FOLLOWERS,
    FOLLOWING
}
