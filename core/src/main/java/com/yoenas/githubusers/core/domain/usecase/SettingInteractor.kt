package com.yoenas.githubusers.core.domain.usecase

import androidx.lifecycle.LiveData
import com.yoenas.githubusers.core.domain.repository.ISettingRepository
import javax.inject.Inject

class SettingInteractor @Inject constructor(private val iSettingRepository: ISettingRepository) :
    SettingUseCase {
    override fun getThemeSetting(): LiveData<Boolean> {
        return iSettingRepository.getThemeSetting()
    }

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        iSettingRepository.saveThemeSetting(isDarkModeActive)
    }
}