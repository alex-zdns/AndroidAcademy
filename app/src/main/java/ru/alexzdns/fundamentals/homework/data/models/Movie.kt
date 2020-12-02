package ru.alexzdns.fundamentals.homework.data.models

data class Movie(
    val title:String,
    val genres: String,
    val runningTimeInMin: Int,
    val ageRating: Int = 13,
    val isLike: Boolean = false,
    val rating: Float,
    val reviewsCount: Int = 0,
    val banner: Int
)