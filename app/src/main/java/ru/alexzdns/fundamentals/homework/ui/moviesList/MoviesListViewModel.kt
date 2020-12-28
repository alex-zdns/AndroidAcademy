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
    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLoadingState = MutableLiveData<Boolean>(false)

    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList
    val loadingState: LiveData<Boolean> get() = _mutableLoadingState

    fun getMovies() {
        viewModelScope.launch {
            _mutableLoadingState.value = true
            val movies = loadMovies(App.context())
            _mutableMoviesList.value = movies
            _mutableLoadingState.value = false
        }
    }
}