package ru.alexzdns.fundamentals.homework.data.repository

import android.content.Context
import androidx.room.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexzdns.fundamentals.homework.data.MoviesAppDataBase
import ru.alexzdns.fundamentals.homework.data.entity.FavoriteMovieEntity
import ru.alexzdns.fundamentals.homework.data.entity.PopularMovieEntity
import ru.alexzdns.fundamentals.homework.data.mappers.MovieMapper
import ru.alexzdns.fundamentals.homework.domain.models.Movie

class MoviesRepository(applicationContext: Context) {
    private val db = MoviesAppDataBase.create(applicationContext)

    suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val favoriteMovie: Set<Long> = getAllFavoriteMovie()
        db.popularMovieDao.getPopularMovies().map { MovieMapper.toMovie(it, favoriteMovie) }
    }

    @Transaction
    suspend fun savePopularMovies(movies: List<Movie>) = withContext(Dispatchers.IO) {
        val movieEntices = movies.map {MovieMapper.toMovieEntity(it)}
        val popularMovieEntices = movies.mapIndexed { index, movie -> PopularMovieEntity(index, movie.id) }

        db.moviesDao.insertMovies(movieEntices)
        db.popularMovieDao.insertAll(popularMovieEntices)
    }


    suspend fun addFavoriteMovieById(movieId: Long) = withContext(Dispatchers.IO) {
        db.favoriteMovieDao.insert(FavoriteMovieEntity(movieId = movieId))
    }

    suspend fun getAllFavoriteMovie(): Set<Long> = withContext(Dispatchers.IO) {
        db.favoriteMovieDao.getAll().map { it.movieId }.toSet()
    }

    suspend fun removeFromFavoriteMovie(movieId: Long) = withContext(Dispatchers.IO) {
        db.favoriteMovieDao.deleteById(movieId)
    }
}