package ru.alexzdns.fundamentals.homework.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie_old
import ru.alexzdns.fundamentals.homework.domain.ActorsDataSource
import ru.alexzdns.fundamentals.homework.domain.MovieDataSource
import ru.alexzdns.fundamentals.homework.ui.adapters.ActorsAdapter

class MovieDetailsFragment : androidx.fragment.app.Fragment() {
    private var listenerMovieDetails: MovieDetailsClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieDetails = context as? MovieDetailsClickListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(MOVIE_ID)?.let { moviePosition ->
            setupView(MovieDataSource.getMovies()[moviePosition])
        }

        val actorsList = view.findViewById<RecyclerView>(R.id.mdf_actors_list)
        val actors = ActorsDataSource.getActors()
        val adapter = ActorsAdapter(actors)
        actorsList.adapter = adapter


        val backButton = view.findViewById<TextView>(R.id.mdf_tv_back_button)
        backButton.setOnClickListener {
            listenerMovieDetails?.removeMovieDetailsFragment()
        }
    }

    private fun setupView(movieOld: Movie_old) {
        view?.run {
            findViewById<TextView>(R.id.mdf_tv_movie_title).text = movieOld.title
            findViewById<TextView>(R.id.mdf_tv_movie_genres).text = movieOld.genres
            findViewById<TextView>(R.id.mdf_tv_age_rating).text = resources.getString(R.string.movie_age_rating, movieOld.ageRating)
            findViewById<TextView>(R.id.mdf_tv_storyline).text = movieOld.storyline
            findViewById<RatingBar>(R.id.mdf_rating_bar).rating = movieOld.rating
            findViewById<TextView>(R.id.mdf_tv_reviews_count).text =
                resources.getQuantityString(R.plurals.reviews_count, movieOld.reviewsCount, movieOld.reviewsCount)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieDetails = null
    }


    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(moviePosition: Int): MovieDetailsFragment =
            MovieDetailsFragment().apply {
                val args = Bundle()
                args.putInt(MOVIE_ID, moviePosition)
                arguments = args
            }
    }


    interface MovieDetailsClickListener {
        fun removeMovieDetailsFragment()
    }
}
