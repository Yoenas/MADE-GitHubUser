package com.yoenas.githubusers.core.data

import com.yoenas.githubusers.core.data.local.LocalDataSource
import com.yoenas.githubusers.core.data.remote.RemoteDataSource
import com.yoenas.githubusers.core.data.remote.network.ApiResponse
import com.yoenas.githubusers.core.data.remote.response.UserResponse
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.core.domain.repository.IUserRepository
import com.yoenas.githubusers.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {

    override fun getSearchUser(query: String): Flow<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun fetchFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getSearchUser(query)
            }
        }.asFlow()
    }

    override fun getDetailUserByUsername(username: String): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            override fun fetchFromNetwork(data: UserResponse): Flow<User> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return remoteDataSource.getDetailUser(username)
            }
        }.asFlow()
    }

    override fun getUserFollower(username: String): Flow<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun fetchFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getUserFollower(username)
            }
        }.asFlow()
    }

    override fun getUserFollowing(username: String): Flow<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun fetchFromNetwork(data: List<UserResponse>): Flow<List<User>> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> {
                return remoteDataSource.getUserFollowing(username)
            }
        }.asFlow()
    }

    override suspend fun insert(user: User) =
        localDataSource.insert(DataMapper.mapDomainToEntity(user))

    override fun getFavoriteUsers(): Flow<List<User>> {
        return localDataSource.getFavoriteUsers().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun delete(user: User) =
        localDataSource.delete(DataMapper.mapDomainToEntity(user))

    override fun getSearchUserFromDB(query: String): Flow<List<User>> {
        return localDataSource.getSearchUserFromDB(query).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
}