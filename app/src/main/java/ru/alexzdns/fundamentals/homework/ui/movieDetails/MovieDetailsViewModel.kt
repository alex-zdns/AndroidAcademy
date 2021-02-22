package ru.alexzdns.fundamentals.homework.ui.movieDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alexzdns.fundamentals.homework.BuildConfig
import ru.alexzdns.fundamentals.homework.data.repository.ActorsRepository
import ru.alexzdns.fundamentals.homework.data.repository.MoviesRepository
import ru.alexzdns.fundamentals.homework.domain.models.Actor
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.network.MovieApi

@Suppress("NAME_SHADOWING")
class MovieDetailsViewModel(
    private val actorsRepository: ActorsRepository,
    private val moviesRepository: MoviesRepository,

    private val movieApi: MovieApi
    ) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    private val _mutableMovie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _mutableMovie

    fun getActors(movieId: Long) {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                val actorsFromDb = actorsRepository.getActorsByMovieId(movieId)
                _mutableState.value = State.Success(actorsFromDb)

                val actors = loadActors(movieId)
                _mutableState.value = State.Success(actors)
                actorsRepository.saveActors(actors, movieId)
            } catch (e: Exception) {
                Log.e("loadActors", e.message ?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    private suspend fun loadActors(movieId: Long): List<Actor> = withContext(Dispatchers.IO) {
        movieApi.getCasts(movieId).cast
            .filter { it.profilePath != null }
            .map { castDTO ->
                Actor(
                    id = castDTO.id,
                    name = castDTO.name,
                    picture = BuildConfig.IMAGE_BASE_URL + BuildConfig.PROFILE_SIZES_PATCH + castDTO.profilePath
                )
            }
    }

    fun getMovie(movieId: Long) {
        viewModelScope.launch {
            val movie = moviesRepository.getMovieById(movieId)

            movie?.let {
                _mutableMovie.value = it
            }
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error: State()
        class Success(val actors: List<Actor>) : State()
    }
}