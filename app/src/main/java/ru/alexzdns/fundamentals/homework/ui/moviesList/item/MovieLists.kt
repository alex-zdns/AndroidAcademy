package ru.alexzdns.fundamentals.homework.ui.moviesList.item

import ru.alexzdns.fundamentals.homework.R

enum class MovieLists(val nameStringId: Int, val path: String) {
    POPULAR(R.string.popular_list_name, "popular"),
    TOP(R.string.top_rated_list_name, "top_rated"),
    NOW_PLAYING(R.string.now_playing_list_name, "now_playing")
}