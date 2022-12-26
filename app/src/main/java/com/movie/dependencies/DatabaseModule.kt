package com.movie.dependencies

import android.content.Context
import com.nmc.myapplication.db.AppDatabase
import com.nmc.myapplication.db.AppDatabaseFactory
import com.nmc.myapplication.db.dao.CacheDao
import com.nmc.myapplication.db.dao.FavMovieDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(context: Context): AppDatabase = AppDatabaseFactory.getDBInstance(context)

    @Provides
    fun provideCacheDao(db: AppDatabase): CacheDao = db.apiCacheDao()

    @Provides
    fun provideFavMovieCacheDao(db: AppDatabase): FavMovieDao = db.favMovieCacheDao()
}
