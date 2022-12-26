package com.nmc.myapplication.db.dao

import androidx.room.*
import com.nmc.myapplication.db.entity.FavMovie

@Dao
interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: FavMovie)

    @Delete()
    fun delete(cache: FavMovie)

    @Query("SELECT EXISTS (SELECT 1 FROM FavMovie WHERE id = :id)")
    fun exists(id: Int): Boolean
}