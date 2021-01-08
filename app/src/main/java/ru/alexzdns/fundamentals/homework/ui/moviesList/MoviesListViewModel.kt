package ru.alexzdns.fundamentals.homework.ui.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import ru.alexzdns.fundamentals.homework.data.MoviesPagingSource

class MoviesListViewModel : ViewModel() {
    val moviesPagingFlow = Pager(PagingConfig(pageSize = 1)) {
        MoviesPagingSource()
    }.flow.cachedIn(viewModelScope)
}