package ru.alexzdns.fundamentals.homework.domain

import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Actor

object ActorsDataSource {
    fun getActors(): List<Actor> {
        return listOf(
            Actor(name = "Robert Downey Jr.", avatar = R.drawable.img_downey_jr),
            Actor(name = "Chris Evans", avatar = R.drawable.img_evans),
            Actor(name = "Mark Ruffalo", avatar = R.drawable.img_ruffalo),
            Actor(name = "Chris Hemsworth", avatar = R.drawable.img_hemsworth),
            Actor(name = "Scarlett Johansson", avatar = R.drawable.img_johansson),
        )
    }
}