package ru.alexzdns.fundamentals.homework.data.repository

import android.content.Context
import androidx.room.Transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexzdns.fundamentals.homework.data.MoviesAppDataBase
import ru.alexzdns.fundamentals.homework.data.mappers.ActorMapper
import ru.alexzdns.fundamentals.homework.domain.models.Actor

class ActorsRepository(applicationContext: Context) {
    private val db = MoviesAppDataBase.create(applicationContext)

    @Transaction
    suspend fun saveActors(actors: List<Actor>, movieId: Long) = withContext(Dispatchers.IO) {
        db.actorsDao.saveList(actors.map { ActorMapper.toActorEntity(it) })
        db.actorsAndMovieDao.saveActorIdsByMovieId(
            ActorMapper.toActorsAndMoviesEntity(
                actors,
                movieId
            )
        )
    }

    @Transaction
    suspend fun getActorsByMovieId(movieId: Long): List<Actor> = withContext(Dispatchers.IO) {
        val actorIds: List<Int> =
            ActorMapper.toActorIds(db.actorsAndMovieDao.getActorIdsByMovieId(movieId))

        actorIds.map {db.actorsDao.getActorsById(it)}.map { ActorMapper.toActor(it) }
    }
}