package ru.alexzdns.fundamentals.homework.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.alexzdns.fundamentals.homework.network.dto.CreditsDto
import ru.alexzdns.fundamentals.homework.network.dto.GenresDTO
import ru.alexzdns.fundamentals.homework.network.dto.MoviesDto

interface MovieApi {
    @GET("genre/movie/list")
    suspend fun getGenres(): GenresDTO

    @GET("movie/{path}")
    suspend fun getMovieList(@Path("path") path: String = "now_playing"): MoviesDto

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(@Path("movie_id") movieId: Long): CreditsDto
}