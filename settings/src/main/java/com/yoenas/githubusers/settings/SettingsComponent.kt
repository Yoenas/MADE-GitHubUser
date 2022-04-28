package com.yoenas.githubusers.settings

import android.content.Context
import com.yoenas.githubusers.di.SettingsModuleDependencies
import com.yoenas.githubusers.settings.ui.SettingsActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [SettingsModuleDependencies::class])
interface SettingsComponent {

    @Component.Builder
    interface Factory {
        fun context(@BindsInstance context: Context): Factory
        fun create(settingsModuleDependencies: SettingsModuleDependencies): Factory
        fun build(): SettingsComponent
    }

    fun inject(activity: SettingsActivity)
}