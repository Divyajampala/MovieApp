package com.nmc.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nmc.myapplication.db.entity.Cache

enum class DataName {
    GENRE
}

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: Cache)

    @Query("SELECT data FROM Cache WHERE dataName = :dataName")
    fun getByDataName(dataName: String): String?
}