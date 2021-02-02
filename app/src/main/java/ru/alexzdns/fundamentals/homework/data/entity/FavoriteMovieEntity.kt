package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(tableName = DbContract.FavoriteMovies.TABLE_NAME)
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbContract.FavoriteMovies.COLUMN_NAME_ID)
    val id: Long = 0,

    @ColumnInfo(name = DbContract.FavoriteMovies.COLUMN_NAME_MOVIE_ID)
    val movieId: Long
)