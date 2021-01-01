package ru.alexzdns.fundamentals.homework.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.alexzdns.fundamentals.homework.network.dto.CreditsResponse
import ru.alexzdns.fundamentals.homework.network.dto.GenresResponse
import ru.alexzdns.fundamentals.homework.network.dto.MoviesResponse

interface MovieService {
    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(@Path("movie_id") movieId: Long): CreditsResponse

    @GET("movie/popular")
    suspend fun getPopularMovie(): MoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(): MoviesResponse
}