package ru.alexzdns.fundamentals.homework.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexzdns.fundamentals.homework.data.MoviesAppDataBase
import ru.alexzdns.fundamentals.homework.data.mappers.ActorMapper
import ru.alexzdns.fundamentals.homework.domain.models.Actor

class ActorsRepository(applicationContext: Context) {
    private val db = MoviesAppDataBase.create(applicationContext)

    suspend fun saveActors(actors: List<Actor>, movieId: Long) = withContext(Dispatchers.IO) {
        db.actorsDao.saveList(actors.map { ActorMapper.toActorEntity(it, movieId) })
    }

    suspend fun getActorsByMovieId(movieId: Long): List<Actor> = withContext(Dispatchers.IO) {
        db.actorsDao.getActorsByMovieId(movieId).map { ActorMapper.toActor(it) }
    }


}