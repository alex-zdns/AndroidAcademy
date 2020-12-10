package ru.alexzdns.fundamentals.homework.data.models

data class Movie_old(
    val title:String,
    val storyline:String,
    val genres: String,
    val runningTimeInMin: Int,
    val ageRating: Int = 13,
    var isLike: Boolean = false,
    val rating: Float,
    val reviewsCount: Int = 0,
    val banner: Int
)