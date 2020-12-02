package ru.alexzdns.fundamentals.homework.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexzdns.fundamentals.homework.R
import ru.alexzdns.fundamentals.homework.domain.MovieDataSource
import ru.alexzdns.fundamentals.homework.ui.adapters.MovieAdapter

class MovieListFragment : androidx.fragment.app.Fragment() {
    private var listenerMovieList: MovieListClickListener? = null

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

        val movieList = view.findViewById<RecyclerView>(R.id.mlf_movie_list)
        val movies = MovieDataSource.getMovie()
        val adapter = MovieAdapter(view.context, movies)
        movieList.adapter = adapter

        val temp = view.findViewById<View>(R.id.mlf_tv_movies_list)
        temp.setOnClickListener {
            listenerMovieList?.openMovieDetailsFragment()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieList = null
    }

    interface MovieListClickListener {
        fun openMovieDetailsFragment()
    }
}
