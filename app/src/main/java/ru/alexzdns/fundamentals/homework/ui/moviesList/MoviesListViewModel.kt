package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.data.loadMovies
import ru.alexzdns.fundamentals.homework.data.models.Movie

class MoviesListViewModel(
    private val context: Context
) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    fun getMovies() {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                val movies = loadMovies(context)
                _mutableState.value = State.Success(movies)
            } catch (e: Exception) {
                _mutableState.value = State.Error()
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