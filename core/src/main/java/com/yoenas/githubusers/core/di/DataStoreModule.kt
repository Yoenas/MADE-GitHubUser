package com.yoenas.githubusers.core.di

import android.content.Context
import com.yoenas.githubusers.core.data.local.datastore.SettingPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providesSettingPreferences(@ApplicationContext context: Context): SettingPreferences =
        SettingPreferences(context)
}