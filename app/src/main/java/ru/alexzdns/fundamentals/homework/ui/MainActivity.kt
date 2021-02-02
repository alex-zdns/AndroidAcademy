package ru.alexzdns.fundamentals.homework.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.ui.movieDetails.MovieDetailsFragment
import ru.alexzdns.fundamentals.homework.ui.moviesList.MoviesListFragment

class MainActivity : AppCompatActivity(),
    MoviesListFragment.MovieListClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MoviesListFragment())
                .commit()
        }
    }

    override fun openMovieDetailsFragment(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(android.R.id.content, MovieDetailsFragment.newInstance(movie))
            .commit()
    }

    override fun removeMovieDetailsFragment() {
        supportFragmentManager.popBackStack()
    }
}
