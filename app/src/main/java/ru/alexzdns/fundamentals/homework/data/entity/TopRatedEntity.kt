package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(tableName = DbContract.TopRated.TABLE_NAME,
    indices = [Index(DbContract.TopRated.COLUMN_NAME_POSITION)]
)
data class TopRatedEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.TopRated.COLUMN_NAME_POSITION)
    val position: Int,

    @ColumnInfo(name = DbContract.TopRated.COLUMN_NAME_MOVIE_ID)
    val movieId: Long
)