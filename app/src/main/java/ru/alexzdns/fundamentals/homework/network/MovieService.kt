package ru.alexzdns.fundamentals.homework.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import ru.alexzdns.fundamentals.homework.network.dto.CreditsResponse
import ru.alexzdns.fundamentals.homework.network.dto.MovieDTO
import ru.alexzdns.fundamentals.homework.network.dto.MoviesResponse

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovie(): MoviesResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(@Path("movie_id") movieId: Long): CreditsResponse

    @GET("movie/{movie_id}")
    fun getMovieDetailsAsync(@Path("movie_id") movieId: Long): Deferred<MovieDTO>
}