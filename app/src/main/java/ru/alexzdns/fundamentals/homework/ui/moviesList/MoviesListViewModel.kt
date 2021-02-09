package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.data.repository.MoviesRepository
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.network.MovieApi
import ru.alexzdns.fundamentals.homework.network.MoviesLoader

class MoviesListViewModel(
    private val repository: MoviesRepository,
    movieApi: MovieApi
) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())
    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList

    private val moviesLoader: MoviesLoader = MoviesLoader(movieApi)
    private var favoriteMovie: Set<Long> = emptySet()

    fun getMovies() {
        viewModelScope.launch {
            try {
                _mutableState.value = State.Loading()

                //TODO add database support

//                if (moviesList.value?.isEmpty() == true) {
//                    getMoviesFromDb()
//                }

                getMoviesFromServer()
                _mutableState.value = State.Success()
            } catch (e: Exception) {
                Log.e("loadMoviesFromDB", e.message ?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    private suspend fun getMoviesFromDb() {
        favoriteMovie = repository.getAllFavoriteMovie()
        val moviesFromDb = repository.getPopularMovies()
        _mutableMoviesList.value = moviesFromDb
    }

    private suspend fun getMoviesFromServer() {
        val movies = moviesLoader.loadMoviesFromServer(favoriteMovie)
        _mutableMoviesList.value = movies

        //TODO add save movies to BD


        //repository.savePopularMovies(movies)
        _mutableState.value = State.Success()
    }

    fun onLikeHandle(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            if (movie.isFavorite) {
                repository.addFavoriteMovieById(movie.id)
            } else {
                repository.removeFromFavoriteMovie(movie.id)
            }

            favoriteMovie = repository.getAllFavoriteMovie()
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error : State()
        class Success : State()
    }
}