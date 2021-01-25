package ru.alexzdns.fundamentals.homework.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.alexzdns.fundamentals.homework.data.MoviesAppDataBase
import ru.alexzdns.fundamentals.homework.data.entity.ActorEntity
import ru.alexzdns.fundamentals.homework.domain.models.Actor

class ActorsRepository(applicationContext: Context) {
    private val db = MoviesAppDataBase.create(applicationContext)

    suspend fun saveActors(actors: List<Actor>, movieId: Long) = withContext(Dispatchers.IO) {
        db.actorsDao.saveList(actors.map { toActorEntity(it, movieId) })
    }

    suspend fun getActorsByMovieId(movieId: Long): List<Actor> = withContext(Dispatchers.IO) {
        db.actorsDao.getActorsByMovieId(movieId).map { toActor(it) }
    }

    private fun toActorEntity(actor: Actor, movieId: Long): ActorEntity =
        ActorEntity(
            id = actor.id,
            name = actor.name,
            picture = actor.picture,
            movieId = movieId
        )

    private fun toActor(actorEntity: ActorEntity) = Actor(
        id = actorEntity.id,
        name = actorEntity.name,
        picture = actorEntity.picture
    )
}