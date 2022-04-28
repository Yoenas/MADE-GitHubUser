package com.yoenas.githubusers.di

import com.yoenas.githubusers.core.domain.usecase.SettingUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface SettingsModuleDependencies {
    fun settingUseCase(): SettingUseCase
}