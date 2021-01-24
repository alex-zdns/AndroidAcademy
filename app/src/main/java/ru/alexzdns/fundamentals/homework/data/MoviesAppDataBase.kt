package ru.alexzdns.fundamentals.homework.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexzdns.fundamentals.homework.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesAppDataBase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

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