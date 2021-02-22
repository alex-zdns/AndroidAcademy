package ru.alexzdns.fundamentals.homework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity
import ru.alexzdns.fundamentals.homework.data.entity.TopRatedEntity

@Dao
interface TopRatedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<TopRatedEntity>)

    @Query("DELETE FROM top_rated")
    suspend fun clearTable()

    @Query("SELECT movies.* FROM movies, top_rated WHERE movies._id == top_rated.movie_id ORDER BY top_rated.position ")
    suspend fun getMovies(): List<MovieEntity>
}