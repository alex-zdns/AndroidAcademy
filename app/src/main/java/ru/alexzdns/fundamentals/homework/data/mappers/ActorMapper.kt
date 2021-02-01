package ru.alexzdns.fundamentals.homework.data.mappers

import ru.alexzdns.fundamentals.homework.data.entity.ActorEntity
import ru.alexzdns.fundamentals.homework.domain.models.Actor

object ActorMapper {
    fun toActorEntity(actor: Actor, movieId: Long): ActorEntity =
        ActorEntity(
            id = actor.id,
            name = actor.name,
            picture = actor.picture,
            movieId = movieId
        )

    fun toActor(actorEntity: ActorEntity) = Actor(
        id = actorEntity.id,
        name = actorEntity.name,
        picture = actorEntity.picture
    )
}