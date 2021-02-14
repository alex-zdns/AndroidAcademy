package ru.alexzdns.fundamentals.homework.background

import ru.alexzdns.fundamentals.homework.domain.models.Movie

interface Notifications {
    fun initialize()
    fun showNotification(movie: Movie)
}