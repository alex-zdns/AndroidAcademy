package ru.alexzdns.fundamentals.homework.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity
import ru.alexzdns.fundamentals.homework.domain.models.Movie

class MoviesRepository(applicationContext: Context) {
    private val db = MoviesAppDataBase.create(applicationContext)


    private fun toMovie(movieEntity: MovieEntity): Movie = Movie(
        id = movieEntity.id,
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.poster,
        backdrop = movieEntity.backdrop,
        ratings = movieEntity.ratings,
        numberOfRatings = movieEntity.numberOfRatings,
        minimumAge = movieEntity.minimumAge,
        genres = movieEntity.genres,
        isFavorite = movieEntity.isFavorite
    )

    private fun toMovieEntity(movie: Movie, position: Int): MovieEntity = MovieEntity(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        poster = movie.poster,
        backdrop = movie.backdrop,
        ratings = movie.ratings,
        numberOfRatings = movie.numberOfRatings,
        minimumAge = movie.minimumAge,
        genres = movie.genres,
        position = position,
        isFavorite = movie.isFavorite
    )

    suspend fun getAllMovie(): List<Movie> = withContext(Dispatchers.IO) {
        db.moviesDao.getAllMovies().map { toMovie(it) }
    }

    suspend fun saveAllMovie(movies: List<Movie>) = withContext(Dispatchers.IO) {
        val entices: List<MovieEntity> = movies.mapIndexed { index, movie -> toMovieEntity(movie, index) }
        db.moviesDao.insertMovies(entices)
    }

    suspend fun setIsFavoriteValueById(movie: Movie) {
        db.moviesDao.setIsFavoriteValueById(movie.id, movie.isFavorite)
    }

}