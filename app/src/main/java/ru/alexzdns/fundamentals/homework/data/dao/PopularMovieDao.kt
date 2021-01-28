package ru.alexzdns.fundamentals.homework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity
import ru.alexzdns.fundamentals.homework.data.entity.PopularMovieEntity

@Dao
interface PopularMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<PopularMovieEntity>)

    @Query("DELETE FROM popular_movies")
    suspend fun clearTable()

    @Query("SELECT movies.* FROM movies, popular_movies WHERE movies._id == popular_movies.movie_id ORDER BY popular_movies.position ")
    suspend fun getPopularMovies(): List<MovieEntity>
}