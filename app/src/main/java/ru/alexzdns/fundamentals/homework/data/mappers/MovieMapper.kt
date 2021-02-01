package ru.alexzdns.fundamentals.homework.data.mappers

import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity
import ru.alexzdns.fundamentals.homework.domain.models.Movie

object MovieMapper {
    fun toMovie(movieEntity: MovieEntity, favoriteMovie: Set<Long>): Movie = Movie(
        id = movieEntity.id,
        title = movieEntity.title,
        overview = movieEntity.overview,
        poster = movieEntity.poster,
        backdrop = movieEntity.backdrop,
        ratings = movieEntity.ratings,
        numberOfRatings = movieEntity.numberOfRatings,
        minimumAge = movieEntity.minimumAge,
        genres = movieEntity.genres,
        isFavorite = favoriteMovie.contains(movieEntity.id)
    )

    fun toMovieEntity(movie: Movie): MovieEntity = MovieEntity(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        poster = movie.poster,
        backdrop = movie.backdrop,
        ratings = movie.ratings,
        numberOfRatings = movie.numberOfRatings,
        minimumAge = movie.minimumAge,
        genres = movie.genres,
    )
}