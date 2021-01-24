package ru.alexzdns.fundamentals.homework

import android.app.Application
import android.content.Context

class MovieApp: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        private var context: Context? = null
        fun context(): Context = context ?: throw IllegalStateException()
    }
}