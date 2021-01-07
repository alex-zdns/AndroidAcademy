package ru.alexzdns.fundamentals.homework.ui.moviesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.data.MoviesPagingSource

class MoviesListViewModel : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    val moviesPagingFlow = Pager(PagingConfig(pageSize = 20)) {
        MoviesPagingSource()
    }.flow.cachedIn(viewModelScope)

    fun getMovies() {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                _mutableState.value = State.Success()
            } catch (e: Exception) {
                Log.e("loadMovies", e.message?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error : State()
        class Success() : State()
    }
}