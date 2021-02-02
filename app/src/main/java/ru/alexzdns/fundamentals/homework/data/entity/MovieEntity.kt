package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(
    tableName = DbContract.Movies.TABLE_NAME,
    indices = [Index(DbContract.Movies.COLUMN_NAME_ID)]
)
data class MovieEntity (
    @PrimaryKey
    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_ID)
    val id: Long,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_TITLE)
    val title: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_OVERVIEW)
    val overview: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_POSTER_PATH)
    val poster: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_BACKDROP_PATH)
    val backdrop: String,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_RATINGS)
    val ratings: Float,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_RATING_COUNT)
    val numberOfRatings: Int,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_MIN_AGE)
    val minimumAge: Int,

    @ColumnInfo(name = DbContract.Movies.COLUMN_NAME_GENRES)
    val genres: String
)