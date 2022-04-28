package com.yoenas.githubusers.di

import com.yoenas.githubusers.core.domain.usecase.SettingInteractor
import com.yoenas.githubusers.core.domain.usecase.SettingUseCase
import com.yoenas.githubusers.core.domain.usecase.UserInteractor
import com.yoenas.githubusers.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun providesUserUseCase(userInteractor: UserInteractor): UserUseCase

    @Binds
    abstract fun providesSettingUseCase(settingInteractor: SettingInteractor): SettingUseCase
}