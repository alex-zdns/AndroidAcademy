package ru.alexzdns.fundamentals.homework.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.alexzdns.fundamentals.homework.network.NetworkModule.API_KEY
import ru.alexzdns.fundamentals.homework.network.dto.GenresResponse
import ru.alexzdns.fundamentals.homework.network.dto.MoviesResponse

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("api_key") api_key: String = API_KEY, @Query("language") language: String = "ru"): MoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") api_key: String = API_KEY, @Query("language") language: String = "ru"): GenresResponse
}