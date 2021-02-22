package ru.alexzdns.fundamentals.homework.ui.moviesList.item

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

    var movieListPath: String = MovieLists.POPULAR.path

    fun getMovies() {
        viewModelScope.launch {
            try {
                _mutableState.value = State.Loading()

                if (moviesList.value?.isEmpty() == true) {
                    getMoviesFromDb()
                }

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
        val moviesFromDb = when (movieListPath) {
            MovieLists.POPULAR.path -> repository.getPopularMovies()
            MovieLists.NOW_PLAYING.path -> repository.getNowPlayingMovies()
            MovieLists.TOP.path -> repository.getTopRatedMovies()
            else -> throw IllegalArgumentException("Unknown Movie List")
        }
        _mutableMoviesList.value = moviesFromDb
    }

    private suspend fun getMoviesFromServer() {
        val movies = moviesLoader.loadMoviesFromServer(favoriteMovie, movieListPath)
        _mutableMoviesList.value = movies
        saveMovieToBD(movies)
        _mutableState.value = State.Success()
    }

    private suspend fun saveMovieToBD(movies: List<Movie>) {
        when (movieListPath) {
            MovieLists.POPULAR.path -> repository.savePopularMovies(movies)
            MovieLists.NOW_PLAYING.path -> repository.saveNowPlayingMovies(movies)
            MovieLists.TOP.path -> repository.saveTopRatedMovies(movies)
            else -> throw IllegalArgumentException("Unknown Movie List")
        }
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