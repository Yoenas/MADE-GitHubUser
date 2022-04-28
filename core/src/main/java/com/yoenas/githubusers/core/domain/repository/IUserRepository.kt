package com.yoenas.githubusers.core.domain.repository

import com.yoenas.githubusers.core.data.Resource
import com.yoenas.githubusers.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getSearchUser(query: String): Flow<Resource<List<User>>>

    fun getDetailUserByUsername(username: String): Flow<Resource<User>>

    fun getUserFollower(username: String): Flow<Resource<List<User>>>

    fun getUserFollowing(username: String): Flow<Resource<List<User>>>

    suspend fun insert(user: User)

    fun getFavoriteUsers(): Flow<List<User>>

    suspend fun delete(user: User)

    fun getSearchUserFromDB(query: String): Flow<List<User>>
}