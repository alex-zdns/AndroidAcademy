package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(tableName = DbContract.Actors.TABLE_NAME,
    indices = [Index(DbContract.Actors.COLUMN_NAME_ID), Index(DbContract.Actors.COLUMN_NAME_MOVIE_ID)])
data class ActorEntity(
    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_ID)
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_NAME)
    val name: String,

    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_PICTURE_PATCH)
    val picture: String,

    @ColumnInfo(name = DbContract.Actors.COLUMN_NAME_MOVIE_ID)
    val movieId: Long
)