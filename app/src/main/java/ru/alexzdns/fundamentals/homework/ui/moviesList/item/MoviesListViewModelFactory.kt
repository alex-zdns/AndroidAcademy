package ru.alexzdns.fundamentals.homework.ui.moviesList.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.create
import ru.alexzdns.fundamentals.homework.MovieApp
import ru.alexzdns.fundamentals.homework.data.repository.MoviesRepository
import ru.alexzdns.fundamentals.homework.network.NetworkModule

class MoviesListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(MoviesRepository(MovieApp.context()), NetworkModule.retrofit.create())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}