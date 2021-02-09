package ru.alexzdns.fundamentals.homework.ui.moviesList

enum class MovieLists(val nameList: String, val path: String) {
    POPULAR("Popular", "popular"),
    TOP("Top rated", "top_rated"),
    NOW_PLAYING("Now playing", "now_playing")
}