package ru.alexzdns.fundamentals.homework

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

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
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<TextView>(R.id.mdf_tv_back_button)
        backButton.setOnClickListener {
            listenerMovieDetails?.removeMovieDetailsFragment()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerMovieDetails = null
    }

    interface MovieDetailsClickListener {
        fun removeMovieDetailsFragment()
    }
}
