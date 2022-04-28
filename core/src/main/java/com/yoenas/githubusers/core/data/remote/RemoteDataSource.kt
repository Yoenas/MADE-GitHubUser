package com.yoenas.githubusers.core.data.remote

import android.util.Log
import com.yoenas.githubusers.core.data.remote.network.ApiResponse
import com.yoenas.githubusers.core.data.remote.network.ApiService
import com.yoenas.githubusers.core.data.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getSearchUser(query: String): Flow<ApiResponse<List<UserResponse>>> =
        flow<ApiResponse<List<UserResponse>>> {
            try {
                val searchUser = apiService.findUserBySearch(query)
                val searchResult = searchUser.items
                if (searchResult.isNullOrEmpty()) {
                    emit(ApiResponse.Error(null))
                } else {
                    emit(ApiResponse.Success(searchResult))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)


    suspend fun getDetailUser(username: String): Flow<ApiResponse<UserResponse>> =
        flow {
            try {
                val userDetail = apiService.findDetailUserByUsername(username)
                emit(ApiResponse.Success(userDetail))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollower(username: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val userFollower = apiService.findUserFollowers(username)
                emit(ApiResponse.Success(userFollower))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val userFollowing = apiService.findUserFollowing(username)
                emit(ApiResponse.Success(userFollowing))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)
}