package ru.alexzdns.fundamentals.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    MovieListFragment.MovieListClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MovieListFragment())
                .commit()
        }
    }

    override fun openMovieDetailsFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(android.R.id.content, MovieDetailsFragment())
            .commit()
    }

    override fun removeMovieDetailsFragment() {
        supportFragmentManager.popBackStack()
    }
}
