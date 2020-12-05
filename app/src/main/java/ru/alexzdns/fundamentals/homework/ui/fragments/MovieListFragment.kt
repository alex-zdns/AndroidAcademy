package ru.alexzdns.fundamentals.homework.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.data.models.Movie
import ru.alexzdns.fundamentals.homework.domain.MovieDataSource
import ru.alexzdns.fundamentals.homework.ui.adapters.MovieAdapter

class MovieListFragment : androidx.fragment.app.Fragment() {
    private var listenerMovieList: MovieListClickListener? = null
    private var recycler: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerMovieList = context as? MovieListClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById<RecyclerView>(R.id.mlf_movie_list)
        val movies = MovieDataSource.getMovie()
        val adapter = MovieAdapter(movies, clickListener)
        recycler?.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieList = null
        recycler = null
    }

    private val clickListener = object : MovieAdapter.OnRecyclerMovieItemClicked {
        override fun onBannerClick(position: Int) {
            listenerMovieList?.openMovieDetailsFragment(position)
        }

        override fun onLikeClick(movie: Movie, position: Int) {
            movie.isLike = !movie.isLike
            recycler?.adapter?.notifyItemChanged(position)
        }
    }

    interface MovieListClickListener {
        fun openMovieDetailsFragment(moviePosition: Int)
    }
}
