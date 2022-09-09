package com.rchyn.githubapp.data.local.room

import androidx.room.*
import com.rchyn.githubapp.data.local.entity.FollowersEntity
import com.rchyn.githubapp.data.local.entity.TypeFollowers
import com.rchyn.githubapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM userentity WHERE username = :username")
    fun getUserByUsername(username: String): Flow<UserEntity>

    @Query("SELECT userentity.* FROM userentity INNER JOIN followersentity ON userentity.id = followersentity.idFollowers WHERE followersentity.username = :username AND followersentity.type = :typeFollowers ")
    fun getFollowers(username: String, typeFollowers: TypeFollowers): Flow<List<UserEntity>>

    @Query("SELECT * FROM userentity WHERE isFavorite = 1")
    fun getUserFavorite(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFollowers(followers: List<FollowersEntity>)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("DELETE FROM userentity WHERE isFavorite = 0")
    suspend fun deleteAllUser()

    @Query("SELECT EXISTS(SELECT * FROM userentity WHERE username = :username AND isFavorite = 1)")
    suspend fun isNewFavorite(username: String): Boolean
}