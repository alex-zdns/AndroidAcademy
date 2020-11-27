package ru.alexzdns.fundamentals.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MovieListFragment.MovieListClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {
    private val movieListFragment =
        MovieListFragment().apply { setListener(this@MainActivity) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.ma_main_container, movieListFragment)
            .commit()
    }

    override fun openMovieDetailsFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(
                R.id.ma_main_container,
                MovieDetailsFragment().apply { setListener(this@MainActivity) })
            .commit()
    }

    override fun removeMovieDetailsFragment() {
        val lastFragment = supportFragmentManager.fragments.last()

        supportFragmentManager.beginTransaction()
            .remove(lastFragment)
            .commit()
    }
}
