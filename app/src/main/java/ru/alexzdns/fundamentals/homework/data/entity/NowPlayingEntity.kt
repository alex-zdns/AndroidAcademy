package ru.alexzdns.fundamentals.homework.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.alexzdns.fundamentals.homework.data.DbContract

@Entity(tableName = DbContract.NowPlaying.TABLE_NAME,
    indices = [Index(DbContract.NowPlaying.COLUMN_NAME_POSITION)]
)
data class NowPlayingEntity(
    @PrimaryKey
    @ColumnInfo(name = DbContract.NowPlaying.COLUMN_NAME_POSITION)
    val position: Int,

    @ColumnInfo(name = DbContract.NowPlaying.COLUMN_NAME_MOVIE_ID)
    val movieId: Long
)