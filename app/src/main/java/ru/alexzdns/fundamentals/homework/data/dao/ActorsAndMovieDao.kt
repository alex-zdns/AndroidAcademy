package ru.alexzdns.fundamentals.homework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexzdns.fundamentals.homework.data.entity.ActorsAndMovieEntity

@Dao
interface ActorsAndMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveActorIdsByMovieId(actorsAndMovieEntity: ActorsAndMovieEntity)

    @Query("SELECT actor_ids from actors_and_movie WHERE movie_id == :movieId")
    fun getActorIdsByMovieId(movieId: Long): String
}