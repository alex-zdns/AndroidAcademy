package ru.alexzdns.fundamentals.homework.ui.movieDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie

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

        arguments?.getParcelable<Movie>(MOVIE)?.let { movie ->
            setupView(movie)
        }

        val backButton = view.findViewById<TextView>(R.id.mdf_tv_back_button)
        backButton.setOnClickListener {
            listenerMovieDetails?.removeMovieDetailsFragment()
        }
    }

    private fun setupView(movie: Movie) {
        view?.run {
            val backdrop = findViewById<ImageView>(R.id.mdf_iv_movie_banner)
            Glide.with(context)
                .load(movie.backdrop)
                .apply(RequestOptions().fitCenter())
                .into(backdrop)

            findViewById<TextView>(R.id.mdf_tv_movie_title).text = movie.title
            findViewById<TextView>(R.id.mdf_tv_movie_genres).text = movie.genres.joinToString(separator = ", ") { it.name }
            findViewById<TextView>(R.id.mdf_tv_age_rating).text = resources.getString(R.string.movie_age_rating, movie.minimumAge)
            findViewById<TextView>(R.id.mdf_tv_storyline).text = movie.overview
            findViewById<RatingBar>(R.id.mdf_rating_bar).rating = movie.ratings / 2.0f
            findViewById<TextView>(R.id.mdf_tv_reviews_count).text =
                resources.getQuantityString(R.plurals.reviews_count, movie.numberOfRatings, movie.numberOfRatings)

            val actors = movie.actors

            if (actors.isNotEmpty()) {
                findViewById<TextView>(R.id.mdf_tv_cast_heading).isVisible = true
                findViewById<RecyclerView>(R.id.mdf_actors_list).run {
                    isVisible = true
                    adapter = ActorsAdapter(actors)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieDetails = null
    }


    companion object {
        private const val MOVIE = "movie"

        fun newInstance(movie: Movie): MovieDetailsFragment =
            MovieDetailsFragment().apply {
                val args = Bundle()
                args.putParcelable(MOVIE, movie)
                arguments = args
            }
    }


    interface MovieDetailsClickListener {
        fun removeMovieDetailsFragment()
    }
}
