package com.yoenas.githubusers.ui.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yoenas.githubusers.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {
    fun getResultFollowers(username: String) = useCase.getUserFollower(username).asLiveData()
}