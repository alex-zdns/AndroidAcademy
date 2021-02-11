package ru.alexzdns.fundamentals.homework

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import ru.alexzdns.fundamentals.homework.background.UpdateMoviesListWorker
import ru.alexzdns.fundamentals.homework.background.WorkRepository

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            UpdateMoviesListWorker.TAG,
            ExistingPeriodicWorkPolicy.REPLACE,
            WorkRepository.updateMoviesRequest
        )
    }

    companion object {
        private var context: Context? = null
        fun context(): Context = context ?: throw IllegalStateException()
    }
}