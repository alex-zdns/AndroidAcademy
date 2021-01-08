package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.BuildConfig
import ru.alexzdns.fundamentals.homework.data.models.Genre
import ru.alexzdns.fundamentals.homework.data.models.Movie
import ru.alexzdns.fundamentals.homework.network.NetworkModule
import ru.alexzdns.fundamentals.homework.network.dto.MovieDTO

class MoviesListViewModel : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    fun getMovies() {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                val movies = loadMovies()
                _mutableState.value = State.Success(movies)
            } catch (e: Exception) {
                Log.e("loadMovies", e.message?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    private suspend fun loadMovies(): List<Movie> {
        val response = NetworkModule.theMovieDBApiService.getPopularMovie()

        val moviesDTO = response.movies
            .map { movieDTO -> movieDTO.id }
            .map { NetworkModule.theMovieDBApiService.getMovieDetailsAsync(movieId = it) }
            .awaitAll()
            .filter { it.backdropPath != null && it.posterPath != null }

        return parseMovie(moviesDTO)
    }

    private fun parseMovie(moviesDTO: List<MovieDTO>): List<Movie> =
        moviesDTO.map { movieDTO ->
            Movie(
                id = movieDTO.id,
                title = movieDTO.title,
                overview = movieDTO.overview ?: "",
                poster = BuildConfig.IMAGE_BASE_URL + BuildConfig.POSTER_SIZES_PATCH + movieDTO.posterPath,
                backdrop = BuildConfig.IMAGE_BASE_URL + BuildConfig.BACKDROP_SIZES_PATCH + movieDTO.backdropPath,
                ratings = movieDTO.voteAverage / 2.0f,
                numberOfRatings = movieDTO.voteCount,
                minimumAge = if (movieDTO.adult) 16 else 13,
                runtime = movieDTO.runtime ?: 0,
                genres = movieDTO.genres.map { genreDTO -> Genre(genreDTO.id, genreDTO.name) }
            )
        }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error : State()
        class Success(val movies: List<Movie>) : State()
    }
}