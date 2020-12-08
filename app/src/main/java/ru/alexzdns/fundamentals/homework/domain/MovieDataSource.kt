package ru.alexzdns.fundamentals.homework.domain

import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie

object MovieDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                title = "Avengers: End Game",
                genres = "Action, Adventure, Drama",
                storyline = """After the devastating events of Avengers: Infinity War, the universe is in ruins.
                    | With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\'
                    |  actions and restore balance to the universe.""".trimIndent(),
                runningTimeInMin = 137,
                rating = 4f,
                reviewsCount = 125,
                banner = R.drawable.img_avengers_movie
            ),
            Movie(
                title = "Tenet",
                genres = "Action, Sci-Fi, Thriller",
                storyline = """Armed with only one word, Tenet, and fighting for the survival of the entire world,
                    | a Protagonist journeys through a twilight world of international espionage on a mission
                    |  that will unfold in something beyond real time.""".trimIndent(),
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
                storyline = "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.",
                runningTimeInMin = 102,
                rating = 4f,
                reviewsCount = 38,
                banner = R.drawable.img_black_widow_movie
            ),
            Movie(
                title = "Wonder Woman 1984",
                genres = "Action, Adventure, Fantasy",
                storyline = """Fast forward to the 1980s as Wonder Woman's next big screen adventure finds her facing
                    | two all-new foes: Max Lord and The Cheetah.""".trimIndent(),
                runningTimeInMin = 120,
                rating = 5f,
                reviewsCount = 74,
                banner = R.drawable.img_wonder_woman_1984_movie
            )
        )
    }
}