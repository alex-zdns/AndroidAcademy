package ru.alexzdns.fundamentals.homework.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.*
import retrofit2.create
import ru.alexzdns.fundamentals.homework.data.repository.MoviesRepository
import ru.alexzdns.fundamentals.homework.network.MoviesLoader
import ru.alexzdns.fundamentals.homework.network.NetworkModule
import ru.alexzdns.fundamentals.homework.ui.moviesList.MovieLists

class UpdateMoviesListWorker(
    private val context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    private val repository: MoviesRepository = MoviesRepository(context)
    private val moviesLoader = MoviesLoader(NetworkModule.retrofit.create())

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.i(this::class.simpleName, "Start update")
            //TODO(переписать на цикл)
            val movies = moviesLoader.loadMoviesFromServer(emptySet(), MovieLists.POPULAR.path)

            movies.forEach { cachingImage(it.poster) }
            movies.forEach { cachingImage(it.backdrop) }

            repository.savePopularMovies(movies)
            Log.i(this::class.simpleName, "Successful update")
            return@withContext Result.success()
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.message?:"")
            return@withContext Result.failure()
        }
    }

    private fun cachingImage(url: String) = Glide.with(context).load(url).diskCacheStrategy(
        DiskCacheStrategy.ALL).submit()

    companion object {
        const val TAG = "Movies update"
    }
}