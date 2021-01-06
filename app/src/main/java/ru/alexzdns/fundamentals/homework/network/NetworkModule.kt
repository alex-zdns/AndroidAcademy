package ru.alexzdns.fundamentals.homework.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import ru.alexzdns.fundamentals.homework.BuildConfig
import ru.alexzdns.fundamentals.homework.data.models.Actor
import ru.alexzdns.fundamentals.homework.data.models.Genre
import ru.alexzdns.fundamentals.homework.data.models.Movie
import ru.alexzdns.fundamentals.homework.network.dto.MovieDTO
import ru.alexzdns.fundamentals.homework.network.interceptors.APIKeyInterceptor
import ru.alexzdns.fundamentals.homework.network.interceptors.LanguagesInterceptor
import java.util.concurrent.TimeUnit


object NetworkModule {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private var page = 1;

    private val contentType = "application/json".toMediaType()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(APIKeyInterceptor())
        .addInterceptor(LanguagesInterceptor("en"))
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    private val theMovieDBApiService: MovieService = retrofit.create()

    suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val genres: Map<Int, Genre> =
            theMovieDBApiService.getGenres().genres.map { Genre(id = it.id, name = it.name) }
                .associateBy { it.id }

        val movieResponse = theMovieDBApiService.getPopularMovie(page)
        page++
        parseMovie(movieResponse.movies, genres)
    }

    private suspend fun parseMovie(moviesDTO: List<MovieDTO>, genres: Map<Int, Genre>):List<Movie> = withContext(Dispatchers.Default) {
        moviesDTO.map { movieDTO ->
            Movie(
                id = movieDTO.id,
                title = movieDTO.title,
                overview = movieDTO.overview,
                poster = "https://image.tmdb.org/t/p/original" + movieDTO.posterPath,
                backdrop = "https://image.tmdb.org/t/p/original" + movieDTO.backdropPath,
                ratings = movieDTO.voteAverage,
                numberOfRatings = movieDTO.voteCount,
                minimumAge = if (movieDTO.adult) 16 else 13,
                runtime = 0,
                genres = movieDTO.genres.map {
                    genres[it] ?: throw IllegalArgumentException("Genre not found")
                }
            )
        }
    }

    suspend fun loadActors(movieId: Long): List<Actor> = withContext(Dispatchers.IO) {
        return@withContext theMovieDBApiService.getCasts(movieId).cast.filter { it.profilePath != null }
            .map { castDTO ->
                Actor(
                    id = castDTO.id,
                    name = castDTO.name,
                    picture = "https://image.tmdb.org/t/p/original" + castDTO.profilePath
                )
            }
    }
}