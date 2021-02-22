package ru.alexzdns.fundamentals.homework.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.create
import ru.alexzdns.fundamentals.homework.MovieApp
import ru.alexzdns.fundamentals.homework.data.repository.ActorsRepository
import ru.alexzdns.fundamentals.homework.data.repository.MoviesRepository
import ru.alexzdns.fundamentals.homework.network.NetworkModule

class MovieDetailsViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(
            ActorsRepository(MovieApp.context()),
            MoviesRepository(MovieApp.context()),
            NetworkModule.retrofit.create()
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}