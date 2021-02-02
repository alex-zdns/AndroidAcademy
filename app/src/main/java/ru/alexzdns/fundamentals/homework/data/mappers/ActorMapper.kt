package ru.alexzdns.fundamentals.homework.data.mappers

import ru.alexzdns.fundamentals.homework.data.entity.ActorEntity
import ru.alexzdns.fundamentals.homework.data.entity.ActorsAndMovieEntity
import ru.alexzdns.fundamentals.homework.domain.models.Actor

object ActorMapper {
    fun toActorEntity(actor: Actor): ActorEntity =
        ActorEntity(
            id = actor.id,
            name = actor.name,
            picture = actor.picture,
        )

    fun toActor(actorEntity: ActorEntity) = Actor(
        id = actorEntity.id,
        name = actorEntity.name,
        picture = actorEntity.picture
    )

    fun toActorsAndMoviesEntity(actors: List<Actor>, movieId: Long): ActorsAndMovieEntity =
        ActorsAndMovieEntity(
            movieId = movieId,
            actorIds = actors.joinToString(separator = ",") { it.id.toString() }
        )

    fun toActorIds(actorIds: String?): List<Int> =
        actorIds?.split(",")?.map { it.toInt() } ?: emptyList()
}