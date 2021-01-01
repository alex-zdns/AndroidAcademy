package ru.alexzdns.fundamentals.homework.network

import retrofit2.http.GET
import ru.alexzdns.fundamentals.homework.network.dto.GenresResponse
import ru.alexzdns.fundamentals.homework.network.dto.MoviesResponse

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovie(): MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresResponse
}