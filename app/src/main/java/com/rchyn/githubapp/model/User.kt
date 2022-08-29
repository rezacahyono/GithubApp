package com.rchyn.githubapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class User(
	val follower: Long,
	val following: Long,
	val name: String,
	val company: String,
	val location: String,
	val avatar: String,
	val repository: Int,
	val username: String
): Parcelable