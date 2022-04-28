package com.yoenas.githubusers.core.data.local.room

import androidx.room.*
import com.yoenas.githubusers.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("SELECT * from userEntity")
    fun getFavorites(): Flow<List<UserEntity>>

    @Query("SELECT * FROM userEntity WHERE login LIKE :querySearch")
    fun getSearchFromDB(querySearch: String): Flow<List<UserEntity>>
}