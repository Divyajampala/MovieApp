package com.nmc.myapplication.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int
)