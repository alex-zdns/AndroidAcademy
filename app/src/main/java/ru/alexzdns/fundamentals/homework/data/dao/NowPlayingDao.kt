package ru.alexzdns.fundamentals.homework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity
import ru.alexzdns.fundamentals.homework.data.entity.NowPlayingEntity

@Dao
interface NowPlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<NowPlayingEntity>)

    @Query("DELETE FROM now_playing")
    suspend fun clearTable()

    @Query("SELECT movies.* FROM movies, now_playing WHERE movies._id == now_playing.movie_id ORDER BY now_playing.position ")
    suspend fun getMovies(): List<MovieEntity>
}