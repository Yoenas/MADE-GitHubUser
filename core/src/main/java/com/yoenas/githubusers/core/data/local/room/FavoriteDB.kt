package com.yoenas.githubusers.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoenas.githubusers.core.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}