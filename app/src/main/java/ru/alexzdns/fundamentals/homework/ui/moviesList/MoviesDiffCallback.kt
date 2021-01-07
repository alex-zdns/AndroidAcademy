package ru.alexzdns.fundamentals.homework.ui.moviesList

import androidx.recyclerview.widget.DiffUtil
import ru.alexzdns.fundamentals.homework.data.models.Movie

class MoviesDiffCallback: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}