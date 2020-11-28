package ru.alexzdns.fundamentals.homework

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

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

        val likeButton = view.findViewById<ImageView>(R.id.mlf_iv_like)
        likeButton.setOnClickListener {
            Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show()
        }

        val filmItem = view.findViewById<View>(R.id.mlf_cl_film_item)
        filmItem.setOnClickListener {
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
