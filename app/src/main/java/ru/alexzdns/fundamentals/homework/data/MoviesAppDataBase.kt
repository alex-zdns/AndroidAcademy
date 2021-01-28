package ru.alexzdns.fundamentals.homework.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexzdns.fundamentals.homework.data.dao.ActorsDao
import ru.alexzdns.fundamentals.homework.data.dao.FavoriteMovieDao
import ru.alexzdns.fundamentals.homework.data.dao.MoviesDao
import ru.alexzdns.fundamentals.homework.data.dao.PopularMovieDao
import ru.alexzdns.fundamentals.homework.data.entity.ActorEntity
import ru.alexzdns.fundamentals.homework.data.entity.FavoriteMovieEntity
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity
import ru.alexzdns.fundamentals.homework.data.entity.PopularMovieEntity

@Database(entities = [MovieEntity::class, ActorEntity::class, PopularMovieEntity::class, FavoriteMovieEntity::class], version = 1)
abstract class MoviesAppDataBase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val actorsDao: ActorsDao
    abstract val popularMovieDao: PopularMovieDao
    abstract val favoriteMovieDao: FavoriteMovieDao

    companion object {
        fun create(applicationContext: Context): MoviesAppDataBase = Room.databaseBuilder(
            applicationContext,
            MoviesAppDataBase::class.java,
            DbContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}