package com.yoenas.githubusers.core.domain.usecase

import androidx.lifecycle.LiveData

interface SettingUseCase {

    fun getThemeSetting(): LiveData<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}