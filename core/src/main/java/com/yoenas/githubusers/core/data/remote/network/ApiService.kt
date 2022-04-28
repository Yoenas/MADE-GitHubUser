package com.yoenas.githubusers.core.data.remote.network

import com.yoenas.githubusers.core.data.remote.response.GitHubUsers
import com.yoenas.githubusers.core.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun findUserBySearch(@Query("q") searchQuery: String): GitHubUsers

    @GET("users/{username}")
    suspend fun findDetailUserByUsername(@Path("username") userDetails: String): UserResponse

    @GET("users/{username}/followers")
    suspend fun findUserFollowers(@Path("username") userFollowers: String): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun findUserFollowing(@Path("username") userFollowing: String): List<UserResponse>
}