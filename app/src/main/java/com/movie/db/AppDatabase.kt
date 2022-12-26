package com.nmc.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nmc.myapplication.db.dao.CacheDao
import com.nmc.myapplication.db.entity.Cache

@Database(
    entities = [Cache::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun apiCacheDao(): CacheDao
}

object AppDatabaseFactory {

    private const val DATABASE_NAME = "AppDatabase.db"

    fun getDBInstance(context: Context) =
        Room.databaseBuilder(context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
}