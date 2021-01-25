package ru.alexzdns.fundamentals.homework.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies ORDER BY position ASC")
    suspend fun getAllMovies() : List<MovieEntity>

    @Query("SELECT * FROM movies WHERE _id == :id")
    suspend fun getMovieById(id: Long): MovieEntity

    @Query("UPDATE movies SET is_favorite = :newValue WHERE _id == :movieId")
    suspend fun setIsFavoriteValueById(movieId: Long, newValue: Boolean)
}