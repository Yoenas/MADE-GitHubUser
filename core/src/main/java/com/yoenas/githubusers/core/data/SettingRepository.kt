package com.yoenas.githubusers.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.yoenas.githubusers.core.data.local.datastore.SettingPreferences
import com.yoenas.githubusers.core.domain.repository.ISettingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(private val preferences: SettingPreferences) :
    ISettingRepository {

    override fun getThemeSetting(): LiveData<Boolean> = preferences.getThemeSetting().asLiveData()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        preferences.saveThemeSetting(isDarkModeActive)
    }

}
