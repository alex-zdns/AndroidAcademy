package ru.alexzdns.fundamentals.homework.data

import android.provider.BaseColumns

object DbContract {
    const val DATABASE_NAME = "MoviesApp.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER_PATH = "poster"
        const val COLUMN_NAME_BACKDROP_PATH = "backdrop"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_RATING_COUNT = "numberOfRatings"
        const val COLUMN_NAME_MIN_AGE = "minimumAge"
        const val COLUMN_NAME_GENRES = "genres"
    }

    object PopularMovies {
        const val TABLE_NAME = "popular_movies"

        const val COLUMN_NAME_POSITION = "position"
        const val COLUMN_NAME_MOVIE_ID = "movie_id"
    }

    object FavoriteMovies {
        const val TABLE_NAME = "favorite_movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_MOVIE_ID = "movie_id"
    }

    object Actors {
        const val TABLE_NAME = "actors"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PICTURE_PATCH = "picture_path"
    }

    object ActorsAndMovie {
        const val TABLE_NAME = "actors_and_movie"

        const val COLUMN_NAME_MOVIE_ID = "movie_id"
        const val COLUMN_NAME_ACTOR_IDS = "actor_ids"
    }
}