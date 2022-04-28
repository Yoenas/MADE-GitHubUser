package com.yoenas.githubusers.core.domain.usecase

import com.yoenas.githubusers.core.data.Resource
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository) :
    UserUseCase {
    override fun getSearchUser(query: String): Flow<Resource<List<User>>> {
        return userRepository.getSearchUser(query)
    }

    override fun getDetailUserByUsername(username: String): Flow<Resource<User>> {
        return userRepository.getDetailUserByUsername(username)
    }

    override fun getUserFollower(username: String): Flow<Resource<List<User>>> {
        return userRepository.getUserFollower(username)
    }

    override fun getUserFollowing(username: String): Flow<Resource<List<User>>> {
        return userRepository.getUserFollowing(username)
    }

    override suspend fun insert(user: User) {
        userRepository.insert(user)
    }

    override fun getFavoriteUsers(): Flow<List<User>> {
        return userRepository.getFavoriteUsers()
    }

    override suspend fun delete(user: User) {
        userRepository.delete(user)
    }

    override fun getSearchUserFromDB(query: String): Flow<List<User>> {
        return userRepository.getSearchUserFromDB(query)
    }
}