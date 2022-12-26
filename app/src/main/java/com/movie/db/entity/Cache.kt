package com.nmc.myapplication.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cache(
    @PrimaryKey
    @ColumnInfo(name = "dataName")
    var dataName: String,

    @ColumnInfo(name = "data")
    var data: String?
)