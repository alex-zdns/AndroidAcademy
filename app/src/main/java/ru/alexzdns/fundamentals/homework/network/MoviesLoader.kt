package ru.alexzdns.fundamentals.homework.network

import ru.alexzdns.fundamentals.homework.BuildConfig
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.network.dto.GenreDto
import ru.alexzdns.fundamentals.homework.network.dto.MovieDto

class MoviesLoader(private val movieApi: MovieApi) {
    suspend fun loadMoviesFromServer(favoriteMovie: Set<Long>): List<Movie> {
        val genresMap = movieApi.getGenres().genres
            .associateBy { it.id }

        val moviesDto = movieApi.getPopularMovie().movies
            .filter { it.backdropPath != null && it.posterPath != null }

        return mapMovie(moviesDto, genresMap, favoriteMovie)
    }

    private fun mapMovie(moviesDto: List<MovieDto>, genres: Map<Int, GenreDto>, favoriteMovie: Set<Long>): List<Movie> =
        moviesDto.map { dto ->
            Movie(
                id = dto.id,
                title = dto.title,
                overview = dto.overview,
                poster = BuildConfig.IMAGE_BASE_URL + BuildConfig.POSTER_SIZES_PATCH + dto.posterPath,
                backdrop = BuildConfig.IMAGE_BASE_URL + BuildConfig.BACKDROP_SIZES_PATCH + dto.backdropPath,
                ratings = dto.voteAverage / 2.0f,
                numberOfRatings = dto.voteCount,
                minimumAge = if (dto.adult) 16 else 13,
                genres = dto.genresIds
                    .map { genres[it] ?: throw IllegalArgumentException("Genre not found") }
                    .joinToString(separator = ", ") { it.name },
                isFavorite = favoriteMovie.contains(dto.id)
            )
        }
}