package ru.alexzdns.fundamentals.homework.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexzdns.fundamentals.homework.data.dao.*
import ru.alexzdns.fundamentals.homework.data.entity.*

@Database(
    entities = [MovieEntity::class, ActorEntity::class, ActorsAndMovieEntity::class,
        FavoriteMovieEntity::class, PopularMovieEntity::class, TopRatedEntity::class, NowPlayingEntity::class],
    version = 1
)
abstract class MoviesAppDataBase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val actorsDao: ActorsDao

    abstract val favoriteMovieDao: FavoriteMovieDao
    abstract val actorsAndMovieDao: ActorsAndMovieDao

    abstract val popularMovieDao: PopularMovieDao
    abstract val topRatedDao: TopRatedDao
    abstract val nowPlayingDao: NowPlayingDao


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