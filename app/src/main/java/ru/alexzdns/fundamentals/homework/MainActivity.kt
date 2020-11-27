package ru.alexzdns.fundamentals.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(),
    MovieListFragment.MovieListClickListener,
    MovieDetailsFragment.MovieDetailsClickListener {
    private var movieListFragment : MovieListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            movieListFragment = MovieListFragment().apply { setListener(this@MainActivity)}
            supportFragmentManager.beginTransaction()
                .add(R.id.ma_main_container, movieListFragment!!, MOVIE_LIST_FRAGMENT)
                .commit()
        } else {
            movieListFragment = supportFragmentManager.findFragmentByTag(MOVIE_LIST_FRAGMENT) as? MovieListFragment
        }
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

    companion object {
        const val MOVIE_LIST_FRAGMENT = "movieListFragment"
    }
}
