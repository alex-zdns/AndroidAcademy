package ru.alexzdns.fundamentals.homework.ui.movieDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.alexzdns.fundamentals.homework.data.models.Actor
import ru.alexzdns.fundamentals.homework.network.NetworkModule

class MovieDetailsViewModel(private val movieId: Long) : ViewModel() {
    private val _mutableState = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _mutableState

    fun getActors() {
        viewModelScope.launch {
            _mutableState.value = State.Loading()
            try {
                val actors = NetworkModule.loadActors(movieId)
                _mutableState.value = State.Success(actors)
            } catch (e: Exception) {
                Log.e("loadActors", e.message ?: "")
                e.printStackTrace()
                _mutableState.value = State.Error()
            }
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()
        class Error : State()
        class Success(val actors: List<Actor>) : State()
    }
}