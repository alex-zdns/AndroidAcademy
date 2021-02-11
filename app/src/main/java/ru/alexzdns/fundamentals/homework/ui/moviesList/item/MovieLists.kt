package ru.alexzdns.fundamentals.homework.ui.moviesList.item

enum class MovieLists(val nameList: String, val path: String) {
    //TODO(переделать name на String_id)
    POPULAR("Popular", "popular"),
    TOP("Top rated", "top_rated"),
    NOW_PLAYING("Now playing", "now_playing")
}