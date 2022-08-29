package com.rchyn.githubapp.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
	val users: List<User>
)