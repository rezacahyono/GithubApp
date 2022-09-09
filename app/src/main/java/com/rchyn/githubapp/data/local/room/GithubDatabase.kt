package com.rchyn.githubapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rchyn.githubapp.data.local.entity.FollowersEntity
import com.rchyn.githubapp.data.local.entity.UserEntity
import com.rchyn.githubapp.util.Constant.GITHUB_DB


@Database(
    entities = [UserEntity::class, FollowersEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java, GITHUB_DB
                ).build().also { INSTANCE = it }
            }
    }
}