package com.movie.dependencies

import android.content.Context
import com.nmc.myapplication.db.AppDatabase
import com.nmc.myapplication.db.AppDatabaseFactory
import com.nmc.myapplication.db.dao.CacheDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(context: Context): AppDatabase = AppDatabaseFactory.getDBInstance(context)

    @Provides
    fun provideApiCacheDao(db: AppDatabase): CacheDao = db.apiCacheDao()
}
