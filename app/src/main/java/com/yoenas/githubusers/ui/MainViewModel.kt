package com.yoenas.githubusers.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yoenas.githubusers.core.domain.usecase.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val settingUseCase: SettingUseCase) :
    ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return settingUseCase.getThemeSetting()
    }
}