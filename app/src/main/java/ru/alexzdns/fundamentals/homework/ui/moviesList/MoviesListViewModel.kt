package ru.alexzdns.fundamentals.homework.ui.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.App
import ru.alexzdns.fundamentals.homework.data.loadMovies
import ru.alexzdns.fundamentals.homework.data.models.Movie

class MoviesListViewModel : ViewModel() {
    private val _mutableLoadingState = MutableLiveData<State>(State.Default())
    val loadingState: LiveData<State> get() = _mutableLoadingState

    fun getMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = State.Loading()
            try {
                val movies = loadMovies(App.context())
                _mutableLoadingState.value = State.Success(movies)
            } catch (e: Exception) {
                _mutableLoadingState.value = State.Error()
            }
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error : State()
        class Success(val movies: List<Movie>) : State()
    }
}