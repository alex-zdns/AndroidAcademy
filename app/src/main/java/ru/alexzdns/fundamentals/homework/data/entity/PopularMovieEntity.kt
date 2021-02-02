package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(tableName = DbContract.PopularMovies.TABLE_NAME,
    indices = [Index(DbContract.PopularMovies.COLUMN_NAME_POSITION)]
)
data class PopularMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.PopularMovies.COLUMN_NAME_POSITION)
    val position: Int,

    @ColumnInfo(name = DbContract.PopularMovies.COLUMN_NAME_MOVIE_ID)
    val movieId: Long
)