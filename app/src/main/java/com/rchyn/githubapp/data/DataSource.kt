package com.rchyn.githubapp.data

import android.content.Context
import com.rchyn.githubapp.model.UserResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.IOException

object DataSource {
    fun getUser(context: Context): UserResponse {
        return try {
            val jsonString = context.assets.open("githubuser.json").bufferedReader().use {
                it.readText()
            }
            Json.decodeFromString(jsonString)
        } catch (e: SerializationException) {
            UserResponse(emptyList())
        } catch (e: IOException) {
            UserResponse(emptyList())
        }
    }
}