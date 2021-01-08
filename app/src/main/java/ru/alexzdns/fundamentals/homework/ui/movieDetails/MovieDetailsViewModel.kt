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
import ru.alexzdns.fundamentals.homework.data.models.Actor
import ru.alexzdns.fundamentals.homework.network.NetworkModule

class MovieDetailsViewModel(private val movieId: Long) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    fun getActors() {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                val actors = loadActors(movieId)
                _mutableState.value = State.Success(actors)
            } catch (e: Exception) {
                Log.e("loadActors", e.message ?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    private suspend fun loadActors(movieId: Long): List<Actor> = withContext(Dispatchers.IO) {
        NetworkModule.theMovieDBApiService.getCasts(movieId).cast
            .filter { it.profilePath != null }
            .map { castDTO ->
                Actor(
                    id = castDTO.id,
                    name = castDTO.name,
                    picture = BuildConfig.IMAGE_BASE_URL + BuildConfig.PROFILE_SIZES_PATCH + castDTO.profilePath
                )
            }
    }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error: State()
        class Success(val actors: List<Actor>) : State()
    }
}