package com.yoenas.githubusers.core.data.local

import com.yoenas.githubusers.core.data.local.entity.UserEntity
import com.yoenas.githubusers.core.data.local.room.FavoriteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val favoriteDao: FavoriteDao) {
    suspend fun insert(userEntity: UserEntity) = favoriteDao.insert(userEntity)

    fun getFavoriteUsers(): Flow<List<UserEntity>> = favoriteDao.getFavorites()

    suspend fun delete(userEntity: UserEntity) = favoriteDao.delete(userEntity)

    fun getSearchUserFromDB(query: String) = favoriteDao.getSearchFromDB(query)
}