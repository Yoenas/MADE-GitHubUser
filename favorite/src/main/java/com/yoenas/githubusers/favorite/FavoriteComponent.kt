package com.yoenas.githubusers.favorite

import android.content.Context
import com.yoenas.githubusers.di.FavoriteModuleDependencies
import com.yoenas.githubusers.favorite.ui.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    @Component.Builder
    interface Factory {
        fun context(@BindsInstance context: Context): Factory
        fun create(favoriteModuleDependencies: FavoriteModuleDependencies): Factory
        fun builder() : FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}