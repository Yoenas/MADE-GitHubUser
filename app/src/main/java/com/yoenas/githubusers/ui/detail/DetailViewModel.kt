package com.yoenas.githubusers.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite get() = _isFavorite

    private val listFavoriteUsers: MutableLiveData<List<User>?> = MutableLiveData()

    init {
        getFavoriteUsers()
        _isFavorite.postValue(false)
    }

    fun showUserIsFavorite(favoriteUser: User?) {
        viewModelScope.launch {
            for (it in listFavoriteUsers.value ?: mutableListOf()) {
                if (favoriteUser?.login == it.login) {
                    _isFavorite.postValue(true)
                    break
                } else {
                    _isFavorite.postValue(false)
                }
            }
        }
    }

    fun checkFavoriteUser(favoriteUser: User?) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                delete(favoriteUser)
            } else {
                insert(favoriteUser)
            }
        }
    }

    private fun insert(favoriteUser: User?) {
        viewModelScope.launch {
            favoriteUser?.let {
                useCase.insert(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(true)
            }
        }
    }

    private fun delete(favoriteUser: User?) {
        viewModelScope.launch {
            if (favoriteUser != null) {
                useCase.delete(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(false)
            }
        }
    }

    private fun getFavoriteUsers() {
        viewModelScope.launch {
            useCase.getFavoriteUsers().collect {
                listFavoriteUsers.postValue(it)
            }
        }
    }

    fun getUserDetails(username: String) = useCase.getDetailUserByUsername(username).asLiveData()
}