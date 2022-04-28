package com.yoenas.githubusers.core.domain.repository

import androidx.lifecycle.LiveData

interface ISettingRepository {

    fun getThemeSetting(): LiveData<Boolean>

    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}