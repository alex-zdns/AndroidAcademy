package ru.alexzdns.fundamentals.homework.background

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import java.util.concurrent.TimeUnit

object WorkRepository {
    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(true)
        .build()

    val updateMoviesRequest = PeriodicWorkRequest.Builder(UpdateMoviesListWorker::class.java, 8, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()
}