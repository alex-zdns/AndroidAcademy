package ru.alexzdns.fundamentals.homework.domain

import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Actor_old

object ActorsDataSource {
    fun getActors(): List<Actor_old> {
        return listOf(
            Actor_old(name = "Robert Downey Jr.", avatar = R.drawable.img_downey_jr),
            Actor_old(name = "Chris Evans", avatar = R.drawable.img_evans),
            Actor_old(name = "Mark Ruffalo", avatar = R.drawable.img_ruffalo),
            Actor_old(name = "Chris Hemsworth", avatar = R.drawable.img_hemsworth),
            Actor_old(name = "Scarlett Johansson", avatar = R.drawable.img_johansson),
        )
    }
}