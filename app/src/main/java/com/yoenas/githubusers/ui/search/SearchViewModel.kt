package com.yoenas.githubusers.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yoenas.githubusers.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private var username: MutableLiveData<String> = MutableLiveData()

    fun setSearch(query: String) {
        if (username.value == query) return
        username.value = query
    }

    fun getSearchUser() = Transformations.switchMap(username) {
        useCase.getSearchUser(it).asLiveData()
    }
}