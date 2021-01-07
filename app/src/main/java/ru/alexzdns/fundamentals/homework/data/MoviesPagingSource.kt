package ru.alexzdns.fundamentals.homework.data

import androidx.paging.PagingSource
import kotlinx.coroutines.awaitAll
import ru.alexzdns.fundamentals.homework.data.models.Genre
import ru.alexzdns.fundamentals.homework.data.models.Movie
import ru.alexzdns.fundamentals.homework.network.NetworkModule
import ru.alexzdns.fundamentals.homework.network.dto.MovieDTO

class MoviesPagingSource : PagingSource<Int, Movie>() {
    private val backend = NetworkModule.theMovieDBApiService
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val nextPageNumber = params.key ?: 1

            val response = backend.getPopularMovie(nextPageNumber)

            val moviesDTO = response.movies
                .map { movieDTO -> movieDTO.id }
                .map { backend.getMovieDetailsAsync(movieId = it)}
                .awaitAll()

            return LoadResult.Page(
                data = parseMovie(moviesDTO),
                prevKey = null, // Only paging forward.
                nextKey = response.page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    private fun parseMovie(moviesDTO: List<MovieDTO>): List<Movie> =
        moviesDTO.map { movieDTO ->
            Movie(
                id = movieDTO.id,
                title = movieDTO.title,
                overview = movieDTO.overview,
                poster = "https://image.tmdb.org/t/p/original" + movieDTO.posterPath,
                backdrop = "https://image.tmdb.org/t/p/original" + movieDTO.backdropPath,
                ratings = movieDTO.voteAverage / 2.0f,
                numberOfRatings = movieDTO.voteCount,
                minimumAge = if (movieDTO.adult) 16 else 13,
                runtime = movieDTO.runtime,
                genres = movieDTO.genres.map { genreDTO -> Genre(genreDTO.id, genreDTO.name) }
            )
        }
}