package com.yoenas.githubusers.core.di

import com.yoenas.githubusers.core.data.SettingRepository
import com.yoenas.githubusers.core.data.UserRepository
import com.yoenas.githubusers.core.domain.repository.ISettingRepository
import com.yoenas.githubusers.core.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesUserRepository(userRepository: UserRepository): IUserRepository

    @Binds
    abstract fun providesThemeRepository(themeRepository: SettingRepository): ISettingRepository
}