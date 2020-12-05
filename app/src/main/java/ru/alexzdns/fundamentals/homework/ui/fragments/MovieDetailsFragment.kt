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
import ru.alexzdns.fundamentals.homework.domain.ActorsDataSource
import ru.alexzdns.fundamentals.homework.domain.MovieDataSource
import ru.alexzdns.fundamentals.homework.ui.adapters.ActorsAdapter

class MovieDetailsFragment : androidx.fragment.app.Fragment() {
    private var listenerMovieDetails: MovieDetailsClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieDetails = context as? MovieDetailsClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        if (arguments != null) {
            val moviePosition = arguments?.getInt(MOVIE_ID)

            if (moviePosition != null) {
                val movie = MovieDataSource.getMovie()[moviePosition]

                val title = view.findViewById<TextView>(R.id.mdf_tv_movie_title)
                title.text = movie.title

                val genres = view.findViewById<TextView>(R.id.mdf_tv_movie_genres)
                genres.text = movie.genres

                val ageRating = view.findViewById<TextView>(R.id.mdf_tv_age_rating)
                ageRating.text =
                    view.resources.getString(R.string.movie_age_rating, movie.ageRating)


                val storyLine = view.findViewById<TextView>(R.id.mdf_tv_storyline)
                storyLine.text = movie.storyline

                val ratingBar = view.findViewById<RatingBar>(R.id.mdf_rating_bar)
                ratingBar.rating = movie.rating

                val reviewsCount = view.findViewById<TextView>(R.id.mdf_tv_reviews_count)
                reviewsCount.text =
                    view.resources.getQuantityString(
                        R.plurals.reviews_count,
                        movie.reviewsCount,
                        movie.reviewsCount
                    )
            }

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actorsList = view.findViewById<RecyclerView>(R.id.mdf_actors_list)
        val actors = ActorsDataSource.getActors()
        val adapter = ActorsAdapter(view.context, actors)
        actorsList.adapter = adapter


        val backButton = view.findViewById<TextView>(R.id.mdf_tv_back_button)
        backButton.setOnClickListener {
            listenerMovieDetails?.removeMovieDetailsFragment()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieDetails = null
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(moviePosition: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt(MOVIE_ID, moviePosition)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    interface MovieDetailsClickListener {
        fun removeMovieDetailsFragment()
    }
}
