package com.yoenas.githubusers.core.utils

import com.yoenas.githubusers.core.data.local.entity.UserEntity
import com.yoenas.githubusers.core.data.remote.response.UserResponse
import com.yoenas.githubusers.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponseToDomain(input: List<UserResponse>): Flow<List<User>> {
        val listUser = ArrayList<User>()
        input.map {
            val user = User(
                login = it.login,
                company = it.company,
                publicRepos = it.publicRepos,
                email = it.email,
                followers = it.followers,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl,
                following = it.following,
                name = it.name,
                location = it.location
            )
            listUser.add(user)
        }
        return flowOf(listUser)
    }

    fun mapResponseToDomain(input: UserResponse): Flow<User> {
        return flowOf(
            User(
                login = input.login,
                company = input.company,
                publicRepos = input.publicRepos,
                email = input.email,
                followers = input.followers,
                avatarUrl = input.avatarUrl,
                htmlUrl = input.htmlUrl,
                following = input.following,
                name = input.name,
                location = input.location
            )
        )
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                login = it.login,
                avatarUrl = it.avatarUrl,
                htmlUrl = it.htmlUrl
            )
        }

    fun mapDomainToEntity(input: User) = UserEntity(
        login = input.login,
        avatarUrl = input.avatarUrl,
        htmlUrl = input.htmlUrl
    )
}