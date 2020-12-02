package ru.alexzdns.fundamentals.homework.domain

import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie

object MovieDataSource {
    fun getMovie(): List<Movie> {
        return listOf(
            Movie(
                title = "Avengers: End Game",
                genres = "Action, Adventure, Drama",
                runningTimeInMin = 137,
                rating = 4f,
                reviewsCount = 125,
                banner = R.drawable.img_avengers_movie
            ),
            Movie(
                title = "Tenet",
                genres = "Action, Sci-Fi, Thriller",
                runningTimeInMin = 97,
                ageRating = 16,
                isLike = true,
                rating = 5f,
                reviewsCount = 98,
                banner = R.drawable.img_tenet_movie
            ),
            Movie(
                title = "Black Widow",
                genres = "Action, Adventure, Sci-Fi",
                runningTimeInMin = 102,
                rating = 4f,
                reviewsCount = 38,
                banner = R.drawable.img_black_widow_movie
            ),
            Movie(
                title = "Wonder Woman 1984",
                genres = "Action, Adventure, Fantasy",
                runningTimeInMin = 120,
                rating = 5f,
                reviewsCount = 74,
                banner = R.drawable.img_wonder_woman_1984_movie
            )
        )
    }
}