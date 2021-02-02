package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(tableName = DbContract.ActorsAndMovie.TABLE_NAME)
data class ActorsAndMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.ActorsAndMovie.COLUMN_NAME_MOVIE_ID)
    val movieId: Long,

    @ColumnInfo(name = DbContract.ActorsAndMovie.COLUMN_NAME_ACTOR_IDS)
    val actorIds: String
)