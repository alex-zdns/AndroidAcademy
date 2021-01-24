package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.BuildConfig
import ru.alexzdns.fundamentals.homework.data.MoviesRepository
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.network.MovieApi
import ru.alexzdns.fundamentals.homework.network.dto.GenreDto
import ru.alexzdns.fundamentals.homework.network.dto.MovieDto

class MoviesListViewModel(
    private val repository: MoviesRepository,
    private val movieApi: MovieApi
) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    fun getMovies() {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                val moviesFromDb = repository.getAllMovie()
                _mutableState.value = State.Success(moviesFromDb)
                val movies = loadMovies()
                repository.saveAllMovie(movies)
                _mutableState.value = State.Success(movies)
            } catch (e: Exception) {
                Log.e("loadMovies", e.message ?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    private suspend fun loadMovies(): List<Movie> {
        val genresMap = movieApi.getGenres().genres
            .associateBy { it.id }

        val moviesDto = movieApi.getPopularMovie().movies
            .filter { it.backdropPath != null && it.posterPath != null }

        return mapMovie(moviesDto, genresMap)
    }

    private fun mapMovie(moviesDto: List<MovieDto>, genres: Map<Int, GenreDto>): List<Movie> =
        moviesDto.map { dto ->
            Movie(
                id = dto.id,
                title = dto.title,
                overview = dto.overview,
                poster = BuildConfig.IMAGE_BASE_URL + BuildConfig.POSTER_SIZES_PATCH + dto.posterPath,
                backdrop = BuildConfig.IMAGE_BASE_URL + BuildConfig.BACKDROP_SIZES_PATCH + dto.backdropPath,
                ratings = dto.voteAverage / 2.0f,
                numberOfRatings = dto.voteCount,
                minimumAge = if (dto.adult) 16 else 13,
                genres = dto.genresIds
                    .map { genres[it] ?: throw IllegalArgumentException("Genre not found") }
                    .joinToString(separator = ", ") { it.name }
            )
        }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error : State()
        class Success(val movies: List<Movie>) : State()
    }
}