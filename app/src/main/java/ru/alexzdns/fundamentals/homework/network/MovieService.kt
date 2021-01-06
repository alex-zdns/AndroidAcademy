package ru.alexzdns.fundamentals.homework.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.alexzdns.fundamentals.homework.network.dto.CreditsResponse
import ru.alexzdns.fundamentals.homework.network.dto.GenresResponse
import ru.alexzdns.fundamentals.homework.network.dto.MoviesResponse

interface MovieService {
    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(@Path("movie_id") movieId: Long): CreditsResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int = 1): MoviesResponse
}