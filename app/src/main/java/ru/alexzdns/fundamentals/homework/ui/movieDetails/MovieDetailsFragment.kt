package ru.alexzdns.fundamentals.homework.ui.movieDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.domain.models.Actor
import ru.alexzdns.fundamentals.homework.domain.models.Movie
import ru.alexzdns.fundamentals.homework.ui.movieDetails.MovieDetailsViewModel.State

class MovieDetailsFragment : androidx.fragment.app.Fragment() {
    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModelFactory() }
    private var listenerMovieDetails: MovieDetailsClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieDetails = context as? MovieDetailsClickListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(this.viewLifecycleOwner, this::setState)

        arguments?.getParcelable<Movie>(MOVIE)?.let { movie ->
            setupView(movie)
            if (viewModel.state.value is State.Default) viewModel.getActors(movie.id)
        }

        arguments?.getLong(MOVIE_ID)?.let { movieId ->
            viewModel.movie.observe(this.viewLifecycleOwner, this::setupView)
            viewModel.getMovie(movieId)
        }

        val backButton = view.findViewById<TextView>(R.id.mdf_tv_back_button)
        backButton.setOnClickListener {
            listenerMovieDetails?.removeMovieDetailsFragment()
        }
    }

    private fun setupView(movie: Movie) {
        view?.let {
            val backdrop = it.findViewById<ImageView>(R.id.mdf_iv_movie_banner)
            Glide.with(it.context)
                .load(movie.backdrop)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions().fitCenter())
                .into(backdrop)

            it.findViewById<TextView>(R.id.mdf_tv_movie_title).text = movie.title
            it.findViewById<TextView>(R.id.mdf_tv_movie_genres).text = movie.genres
            it.findViewById<TextView>(R.id.mdf_tv_age_rating).text = resources.getString(R.string.movie_age_rating, movie.minimumAge)
            it.findViewById<TextView>(R.id.mdf_tv_storyline).text = movie.overview
            it.findViewById<RatingBar>(R.id.mdf_rating_bar).rating = movie.ratings
            it.findViewById<TextView>(R.id.mdf_tv_reviews_count).text =
                resources.getQuantityString(R.plurals.reviews_count, movie.numberOfRatings, movie.numberOfRatings)
        }
    }

    private fun setState(state: State) =
        when (state) {
            is State.Default,
            is State.Loading -> {}
            is State.Error -> {
                Toast.makeText(context, getString(R.string.loading_actors_error_message), Toast.LENGTH_LONG).show()
            }
            is State.Success -> {
                setupAdapter(state.actors)
            }
        }

    private fun setupAdapter(actors: List<Actor>) {
        if (actors.isNotEmpty()) {
            view?.let {
                it.findViewById<TextView>(R.id.mdf_tv_cast_heading).isVisible = true
                it.findViewById<RecyclerView>(R.id.mdf_actors_list).run {
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
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movie: Movie): MovieDetailsFragment =
            MovieDetailsFragment().apply {
                val args = Bundle()
                args.putParcelable(MOVIE, movie)
                arguments = args
            }

        fun newInstance(movieId: Long): MovieDetailsFragment =
            MovieDetailsFragment().apply {
                val args = Bundle()
                args.putLong(MOVIE_ID, movieId)
                arguments = args
            }
    }


    interface MovieDetailsClickListener {
        fun removeMovieDetailsFragment()
    }
}

