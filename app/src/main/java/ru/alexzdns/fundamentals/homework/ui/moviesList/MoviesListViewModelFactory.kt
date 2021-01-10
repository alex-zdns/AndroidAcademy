package ru.alexzdns.fundamentals.homework.ui.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexzdns.fundamentals.homework.network.MovieApi
import ru.alexzdns.fundamentals.homework.network.NetworkModule

class MoviesListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(NetworkModule.retrofit.create(MovieApi::class.java))
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}