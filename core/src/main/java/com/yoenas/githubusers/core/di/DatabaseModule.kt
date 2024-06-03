package com.yoenas.githubusers.core.di

import android.content.Context
import androidx.room.Room
import com.yoenas.githubusers.core.data.local.room.FavoriteDB
import com.yoenas.githubusers.core.data.local.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): FavoriteDB {
        val factory = SupportFactory(SQLiteDatabase.getBytes("githubuser".toCharArray()))
        return Room.databaseBuilder(
            context,
            FavoriteDB::class.java,
            "favorite_users.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun providesDao(favoriteDB: FavoriteDB): FavoriteDao {
        return favoriteDB.favoriteDao()
    }
}