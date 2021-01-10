package ru.alexzdns.fundamentals.homework.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.alexzdns.fundamentals.homework.network.MovieApi
import ru.alexzdns.fundamentals.homework.network.NetworkModule

class MovieDetailsViewModelFactory(): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MovieDetailsViewModel::class.java -> MovieDetailsViewModel(NetworkModule.retrofit.create(MovieApi::class.java))
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}