package ru.alexzdns.fundamentals.homework.network

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.alexzdns.fundamentals.homework.data.models.Actor
import ru.alexzdns.fundamentals.homework.data.models.Genre
import ru.alexzdns.fundamentals.homework.data.models.Movie
import java.util.concurrent.TimeUnit


object NetworkModule {
    const val API_KEY = "79827c34a6d2d04b58c4b364e37255a9"
    private val baseUrl = "https://api.themoviedb.org/3/"

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        //.client(httpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    private val theMovieDBApiService: MovieService = retrofit.create()

    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO)  {
        Log.i("load_movie", "load genres is started")
        val genres: Map<Int, Genre> = theMovieDBApiService.getGenres().genres.map { Genre(id = it.id, name = it.name) }.associateBy { it.id }

        Log.i("load_movie", "load genres is succeed")

        return@withContext theMovieDBApiService.getPopularMovie().movies.map { movieDTO ->
            Movie(
                id = movieDTO.id,
                title = movieDTO.title,
                overview = movieDTO.overview,
                poster = "https://image.tmdb.org/t/p/original" + movieDTO.posterPath,
                backdrop = "https://image.tmdb.org/t/p/original" + movieDTO.backdropPath,
                ratings = movieDTO.voteAverage,
                numberOfRatings = movieDTO.voteCount,
                minimumAge = if (movieDTO.adult) 16 else 13,
                runtime =  0,
                genres = movieDTO.genres.map {
                    genres[it] ?: throw IllegalArgumentException("Genre not found")
                },
                actors = emptyList<Actor>()
            )
        }
    }
}