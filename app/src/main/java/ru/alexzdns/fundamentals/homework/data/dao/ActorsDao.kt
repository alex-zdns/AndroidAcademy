package ru.alexzdns.fundamentals.homework.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexzdns.fundamentals.homework.data.entity.ActorEntity

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveList(actorEntity: List<ActorEntity>)

    @Query("SELECT * FROM actors WHERE movie_id == :movieId")
    suspend fun getActorsByMovieId(movieId: Long): List<ActorEntity>
}