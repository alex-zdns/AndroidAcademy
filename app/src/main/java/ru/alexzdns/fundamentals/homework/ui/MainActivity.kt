package ru.alexzdns.fundamentals.homework.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.ui.movieDetails.MovieDetailsFragment
import ru.alexzdns.fundamentals.homework.ui.moviesList.MoviesListFragment
import ru.alexzdns.fundamentals.homework.ui.moviesList.item.MoviesListItemFragment

class MainActivity : AppCompatActivity(),
    MoviesListItemFragment.MovieListClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MoviesListFragment())
                .commit()

            intent?.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                val id = intent.data?.lastPathSegment?.toLongOrNull()
                if (id != null) {
                    openMovieDetailsFragment(id)
                }
            }
        }
    }

    override fun openMovieDetailsFragment(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(android.R.id.content, MovieDetailsFragment.newInstance(movie))
            .commit()
    }

    override fun openMovieDetailsFragment(movieId: Long) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(android.R.id.content, MovieDetailsFragment.newInstance(movieId))
            .commit()
    }

    override fun removeMovieDetailsFragment() {
        supportFragmentManager.popBackStack()
    }
}
