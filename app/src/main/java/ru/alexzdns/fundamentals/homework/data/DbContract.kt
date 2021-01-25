package ru.alexzdns.fundamentals.homework.data

import android.provider.BaseColumns

object DbContract {
    const val DATABASE_NAME = "MoviesApp.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_OVERVIEW = "overview"
        const val COLUMN_NAME_POSTER_PATCH = "poster"
        const val COLUMN_NAME_BACKDROP_PATH = "backdrop"
        const val COLUMN_NAME_RATINGS = "ratings"
        const val COLUMN_NAME_RATING_COUNT = "numberOfRatings"
        const val COLUMN_NAME_MIN_AGE = "minimumAge"
        const val COLUMN_NAME_GENRES = "genres"
        const val COLUMN_NAME_POSITION = "position"
        const val COLUMN_NAME_IS_FAVORITE = "is_favorite"
    }
}