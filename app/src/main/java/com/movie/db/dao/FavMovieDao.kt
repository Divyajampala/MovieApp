package com.nmc.myapplication.db.dao

import androidx.room.*
import com.nmc.myapplication.db.entity.FavMovieCache

@Dao
interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: FavMovieCache)

    @Delete()
    fun delete(cache: FavMovieCache)

    @Query("SELECT EXISTS (SELECT 1 FROM FavMovieCache WHERE id = :id)")
    fun exists(id: Int): Boolean
}