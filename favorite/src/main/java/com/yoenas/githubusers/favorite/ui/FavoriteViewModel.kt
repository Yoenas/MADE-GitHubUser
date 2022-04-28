package com.yoenas.githubusers.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yoenas.githubusers.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    fun getFavoriteUsers() = useCase.getFavoriteUsers().asLiveData()

    fun getSearchUserFromDB(query: String) = useCase.getSearchUserFromDB(query).asLiveData()
}